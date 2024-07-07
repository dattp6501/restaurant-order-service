package com.dattp.order.service;

import com.dattp.order.config.kafka.TopicKafkaConfig;
import com.dattp.order.config.redis.RedisKeyConfig;
import com.dattp.order.dto.bookedtable.BookedTableResponseDTO;
import com.dattp.order.dto.booking.BookingResponseDTO;
import com.dattp.order.entity.Booking;
import com.dattp.order.entity.BookingTransaction;
import com.dattp.order.entity.action.BookingActionType;
import com.dattp.order.entity.state.BookedTableState;
import com.dattp.order.entity.state.BookingState;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.exception.InternalServerException;
import com.dattp.order.pojo.booking.BookingOverview;
import com.dattp.order.pojo.user.UserOverview;
import com.dattp.order.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BookingService extends com.dattp.order.service.Service {
    @Autowired
    @Lazy
    private BookingService self;

    //=============================================CUSTOMER
    public List<BookingResponseDTO> findAllFromDBAndCache(Long id, BookingState state, LocalDateTime from, LocalDateTime to, Boolean paid, Pageable pageable) {
        String key = RedisKeyConfig.genKeyListOrder(jwtService.getUserId());
        List<BookingOverview> bos = redisService.getHashAll(key, BookingOverview.class);
        log.debug("=========> findAllFromDBAndCache:Cache:{}", Objects.nonNull(bos) ? bos.size() : null);
        if (Objects.isNull(bos)) {
            Long fromMills = Objects.nonNull(from) ? DateUtils.getMills(from) : null;
            Long toMills = Objects.nonNull(to) ? DateUtils.getMills(to) : null;
            Map<Object, Object> hasn = bookingStorage.findAll(id, state, fromMills, toMills, jwtService.getUserId(), null, paid, pageable).stream()
                .map(BookingOverview::new)
                .collect(Collectors.toMap(e -> e.getId().toString(), Function.identity()));
            redisService.putHashAll(key, hasn, RedisService.CacheTime.ONE_DAY);
            bos = hasn.values().stream()
                .map(e -> (BookingOverview) e)
                .collect(Collectors.toList());
            log.debug("=========> findAllFromDBAndCache:DB:{}", bos.size());
        }
        return bos.stream().map(BookingResponseDTO::new)
            .collect(Collectors.toList());
    }

    public BookingResponseDTO getBookingDetailFromDBAndCache(Long id) {
        String key = RedisKeyConfig.genKeyOrderDetail(jwtService.getUserId(), id);
        Booking booking = redisService.getEntity(key, Booking.class);
        log.debug("========> getBookingDetailFromDBAndCache:Cache:{}", booking);
        if (Objects.isNull(booking)) {
            booking = bookingStorage.findById(id);
            redisService.setEntity(key, booking, RedisService.CacheTime.ONE_DAY);
        }
        return new BookingResponseDTO(booking);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BookingResponseDTO save(Booking booking) {
        //save
        Booking newBooking = bookingStorage.save(booking);
        //delete dish in cart
        redisService.deleteHashs(
            RedisKeyConfig.genKeyCartDish(newBooking.getCustomerId()),
            newBooking.getDishs().stream()
                .map(e -> e.getDishId().toString())
                .collect(Collectors.toList())
        );
        //response
        BookingResponseDTO resp = new BookingResponseDTO(newBooking);
        //send kafka -> check info
        if (!kafkaService.send(TopicKafkaConfig.NEW_BOOKING_TOPIC, resp)) throw new InternalServerException();
        return resp;
    }

    // ==============================================EMPLOYEE
    public List<BookingResponseDTO> findAllFromDB(Long id, BookingState state, LocalDateTime from, LocalDateTime to, String custemerFullname, Boolean paid, Pageable pageable) {
        Long fromMills = Objects.nonNull(from) ? DateUtils.getMills(from) : null;
        Long toMills = Objects.nonNull(to) ? DateUtils.getMills(to) : null;
        return bookingStorage.findAll(id, state, fromMills, toMills, null, custemerFullname, paid, pageable).stream()
            .map(BookingResponseDTO::new)
            .collect(Collectors.toList());
    }

    public BookingResponseDTO getBookingDetailFromDB(Long id) {
        Booking booking = bookingStorage.findById(id);
        return new BookingResponseDTO(booking);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingStorage.findById(bookingId);
        if (booking.getState().equals(BookingState.CANCEL)) throw new BadRequestException("Phiếu đã được đóng");
        //save booking
        Booking newBooking = bookingStorage.cancel(booking);
        //transaction
        BookingTransaction bkt = new BookingTransaction();
        bkt.setBookingInfo(new BookingOverview(booking));
        bkt.setActionType(BookingActionType.CANCEL);
        bkt.setUserInfo(getEmployeeInfo());
        bookingTransactionStorage.save(bkt);
        //
        kafkaService.send(TopicKafkaConfig.CANCEL_BOOKING_TOPIC, new BookingResponseDTO(newBooking));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void confirmBooking(Long bookingId) {
        Booking booking = bookingStorage.findById(bookingId);
        //If the order is not check order
        if (booking.getState().equals(BookingState.NEW))
            throw new BadRequestException("Hệ thống đang kiểm tra phiếu đặt này");
        //If the order is not waiting
        if (booking.getState().equals(BookingState.SUCCESS))
            throw new BadRequestException("Phiếu đã được xử lý");
        if (booking.getBookedTables().stream()
            .anyMatch(bt -> bt.getState().equals(BookedTableState.NOT_FOUND))
        ) throw new BadRequestException("Phiếu đặt bàn chứa bàn không tồn tại");
        //
        Booking newBooking = bookingStorage.confirm(booking);
        //transaction
        BookingTransaction bkt = new BookingTransaction();
        bkt.setBookingInfo(new BookingOverview(booking));
        bkt.setActionType(BookingActionType.CONFIRM);
        bkt.setUserInfo(getEmployeeInfo());
        bookingTransactionStorage.save(bkt);
        // update state booked table
        kafkaService.send(TopicKafkaConfig.CONFIRM_BOOKING_TOPIC, new BookingResponseDTO(newBooking));
    }

    private UserOverview getEmployeeInfo() {
        UserOverview userInfo = new UserOverview();
        try {
            userInfo.setId(jwtService.getUserId());
            userInfo.setMail(jwtService.getMail());
            userInfo.setFullname(jwtService.getFullname());
            userInfo.setUsername(jwtService.getUsername());
        } catch (Exception e) {
            log.debug("=======> getEmployeeInfo:{}", e.getMessage());
        }
        return userInfo;
    }


    //===============   SCHEDULE   =======================

    public void processOrderNotPaid() {
        List<Long> bookingIds = bookingStorage.findAllNotPaid();
        bookingIds.forEach(id -> {
            try {
                self.cancelBooking(id);
            } catch (Exception e) {
                log.error("=======> processOrderNotPaid:{}", e.getMessage());
            }
        });
    }

    //================================= KAFKA =======================================
    public void checkAndUpdateBooking(BookingResponseDTO dto) {
        try {
            // neu don hang khong ton tai
            Booking booking = bookingStorage.findById(dto.getId());
            //thong tin nhan duoc la thong tin cuoi cung cua booking
            if (!booking.getState().equals(BookingState.NEW)) return;
            //lay ra nhung ban co trong du lieu nhan duoc
            if (Objects.isNull(dto.getBookedTables())) dto.setBookedTables(new ArrayList<>());
            Map<Long, BookedTableResponseDTO> mapTable = dto.getBookedTables().stream()
                .collect(Collectors.toMap(BookedTableResponseDTO::getTableId, Function.identity()));
            //cap nhat lai thong tin nhung ban sau khi product serivce da kiem tra va cap nhat
            booking.getBookedTables().forEach(e -> {
                BookedTableResponseDTO bt = mapTable.get(e.getTableId());
                e.setName(bt.getName());
                e.setState(bt.getState().equals(BookedTableState.DELETE) ? BookedTableState.NOT_FOUND : BookedTableState.PROCESSING);
                e.setPrice(bt.getPrice());
                e.setUpdateAt(DateUtils.getCurrentMils());
            });
            booking.setState(BookingState.PROCESSING);
            bookingStorage.save(booking);
        } catch (BadRequestException e) {
            log.error("==================> checkAndUpdateBooking:BadRequestException:{}", e.getMessage());
        }
    }
}
