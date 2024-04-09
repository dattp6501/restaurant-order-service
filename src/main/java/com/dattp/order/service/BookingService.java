package com.dattp.order.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.dattp.order.config.kafka.TopicKafkaConfig;
import com.dattp.order.config.redis.RedisKeyConfig;
import com.dattp.order.dto.bookedtable.BookedTableResponseDTO;
import com.dattp.order.dto.booking.BookingResponseDTO;
import com.dattp.order.entity.BookedTable;
import com.dattp.order.entity.state.BookedTableState;
import com.dattp.order.entity.state.BookingState;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.dattp.order.entity.Booking;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class BookingService extends com.dattp.order.service.Service {
    public BookingResponseDTO save(Booking booking){
        //save
        Booking newBooking = bookingStorage.save(booking);
        //delete table in cart
        redisService.deleteHashs(
            RedisKeyConfig.genKeyCartTable(newBooking.getCustomerId()),
            newBooking.getBookedTables().stream()
                .map(e->e.getId().toString())
                .collect(Collectors.toList())
        );
        //response
        BookingResponseDTO resp = new BookingResponseDTO(newBooking);
        //send kafka -> check table
        kafkaService.send(TopicKafkaConfig.NEW_BOOKING_TOPIC, resp);
        return resp;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void checkAndUpdateBooking(BookingResponseDTO dto){
        try {
            // neu don hang khong ton tai
            Booking booking = bookingStorage.findById(dto.getId());
            //thong tin nhan duoc la thong tin cuoi cung cua booking
            if(!booking.getState().equals(BookingState.NEW)) return;
            //lay ra nhung ban co trong du lieu nhan duoc
            if(Objects.isNull(dto.getBookedTables())) dto.setBookedTables(new ArrayList<>());
            Map<Long, BookedTableResponseDTO> mapTable = dto.getBookedTables().stream()
                .collect(Collectors.toMap(BookedTableResponseDTO::getTableId, Function.identity()));
            //cap nhat lai thong tin nhung ban sau khi product serivce da kiem tra va cap nhat
            booking.getBookedTables().forEach(e->{
                BookedTableResponseDTO bt = mapTable.get(e.getTableId());
                e.setName(bt.getName());
                e.setState(bt.getState().equals(BookedTableState.DELETE)?BookedTableState.NOT_FOUND:BookedTableState.PROCESSING);
                e.setPrice(bt.getPrice());
                e.setUpdateAt(DateUtils.getCurrentMils());
            });
            booking.setState(BookingState.PROCESSING);
            bookingStorage.save(booking);
        }catch (BadRequestException e){
            log.error("==================> checkAndUpdateBooking:BadRequestException:{}", e.getMessage());
        }
    }

//    public Page<Booking> getByCustemerId(long custemerId, Pageable pageable){
//        return bookingRepository.getAllByCustomerId(custemerId, pageable);
//    }
//
//    public void delete(Long id){
//        Booking booking = bookingRepository.findById(id).orElse(null);
//        if(booking==null) throw new NotfoundException("Đơn hàng không tồn tại");
//        bookingRepository.delete(booking);
//    }
//    private float getDepositsBooking(BookingResponseDTO bookingResponseDTO){
//        return 100000;
//    }

//    @Transactional
//    public void addDishToBooking(Long bookingId, List<BookedDish> dishs){
//        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
//        if(booking.getState().equals(BookingState.PROCESSING))
//            throw new BadRequestException("Phiếu đặt bàn đang được xử lý, vui lòng đặt món sau khi xử lý xong");
//        for(BookedDish bd: dishs){//set booking for dish
//            bd.setBooking(booking);
//            // dish was placed on the menu
//            if(booking.getDishs().contains(bd)) throw new BadRequestException(bd.getName() + "Đã có trong thực đơn");
//        }
//        bookedDishRepository.saveAll(dishs);
//        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());//get id user from access_token
//        // send message to check
//        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
//        bookingResponseDTO.setId(bookingId);
//        bookingResponseDTO.setDishs(new ArrayList<>());
//        dishs.forEach((bd)->{
//            BookedDishResponseDTO bdResp = new BookedDishResponseDTO();
//            BeanUtils.copyProperties(bd, bdResp);
//            bookingResponseDTO.getDishs().add(bdResp);
//        });
////        bookingKafkaTemplate.send("checkBookedDish",bookingResponseDTO);
//    }
//
//    @Transactional
//    public void checkAndUpdateDish(Booking booking){
//        // neu don hang khong ton tai
//        if(!bookingRepository.existsById(booking.getId())) return;
//        booking.getDishs().forEach((bd)->{
//            if(bookedDishRepository.existsById(bd.getId())){// if dish exist
//                if(bd.getState()==ApplicationConfig.NOT_FOUND_STATE)//delete dish not found
//                    bookedDishRepository.deleteById(bd.getId());
//                else bookedDishService.update(bd);// update full info dish
//            }
//        });
//    }
//
//
//    // EMPLOYEE
//    public List<Booking> getAllByFromAndTo(Date from, Date to, Pageable pageable){
//        List<Booking> list = bookingRepository.findAllByFromAndTo(from, to, pageable).toList();
//        return list;
//    }
//
//    @Transactional
//    public void cancelBooking(Long bookingId) throws Exception{
//        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
//        if(booking.getState().equals(BookingState.CANCEL))
//            throw new Exception("Phiếu đã được đóng");
//        bookedDishRepository.deleteAll(booking.getDishs());
//        booking.getBookedTables().forEach((t)->{
//            // update state booked
//            t.setState(BookedTableState.CANCEL);
//            bookedTableService.update(t);
//        });
//        bookingRepository.updateState(bookingId, ApplicationConfig.CANCEL_STATE);
//    }
//
//    @Transactional
//    public void confirmBooking(Long bookingId) throws Exception{
//        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
//        //If the order is not check order
//        if(booking.getState().equals(BookingState.PROCESSING))
//            throw new Exception("Hệ thống đang kiểm tra phiếu đặt này");
//        //If the order is not waiting
//        if(booking.getState().equals(BookingState.SUCCESS))
//            throw new Exception("Phiếu đã được xử lý");
//
//        // update state booked table
//        booking.getBookedTables().forEach((t)->{
//            t.setState(BookedTableState.SUCCESS);
//            bookedTableService.update(t);
//        });
//        // update state booked dish
//        if(!booking.getDishs().isEmpty()){
//            booking.getDishs().forEach((d)->{
//                d.setState(ApplicationConfig.OK_STATE);
//                bookedDishService.update(d);
//            });
//        }
//        bookingRepository.updateState(bookingId, ApplicationConfig.OK_STATE);
//
//        // update success
//        BookingResponseDTO bkResp = new BookingResponseDTO();
//        BeanUtils.copyProperties(booking, bkResp);
//        // table
//        bkResp.setBookedTables(new ArrayList<>());
//        booking.getBookedTables().forEach((t)->{
//            BookedTableResponseDTO tResp = new BookedTableResponseDTO();
//            BeanUtils.copyProperties(t, tResp);
//            bkResp.getBookedTables().add(tResp);
//        });
//        // dish
//        if(!booking.getDishs().isEmpty()){
//            booking.getDishs().forEach((d)->{
//                BookedDishResponseDTO dResp = new BookedDishResponseDTO();
//                BeanUtils.copyProperties(d, dResp);
//                bkResp.getDishs().add(dResp);
//            });
//        }
////        bookingKafkaTemplate.send("createPaymentOrder",bkResp);//send info order need payment
////        bookingKafkaTemplate.send("notiOrder",bkResp);//send notification order success, required payment
//    }
//
//    @Transactional
//    public int updatePaid(Long bookingId, boolean paid){
//        return bookingRepository.updatePaid(bookingId, paid);
//    }
}
