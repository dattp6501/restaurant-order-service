package com.dattp.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dattp.order.config.ApplicationConfig;
import com.dattp.order.dto.BookedDishResponseDTO;
import com.dattp.order.dto.BookedTableResponseDTO;
import com.dattp.order.dto.BookingResponseDTO;
import com.dattp.order.entity.BookedDish;
import com.dattp.order.entity.BookedTable;
import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.exception.NotfoundException;
import com.dattp.order.repository.BookedDishRepository;
import com.dattp.order.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookedDishRepository bookedDishRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private BookedTableService bookedTableService;
    @Autowired
    private BookedDishService bookedDishService;
//    @Autowired
//    private KafkaTemplate<String,BookingResponseDTO> bookingKafkaTemplate;

    @Transactional
    public Booking save(Booking booking){
        booking.getBookedTables().forEach(b->{
            if(!bookedTableService.isFreetime(b)){//ban da duoc thue trong khoang thoi gian nay
                throw new BadRequestException("Bàn name = "+b.getName()+" đã được thuê trong khoảng thòi gian này");
            }
        });
        Booking newBooking = bookingRepository.save(booking);
        cartService.deleteAllTable(newBooking.getCustomerId());
        return newBooking;
    }

    public Booking getByID(Long id){
        return bookingRepository.findById(id).orElse(null);
    }

    public Page<Booking> getByCustemerId(long custemerId,Pageable pageable){
        return bookingRepository.getAllByCustomerId(custemerId, pageable);
    }

    public void delete(Long id){
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking==null) throw new NotfoundException("Đơn hàng không tồn tại");
        bookingRepository.delete(booking);
    }
    
    @Transactional
    public void checkAndUpdateBooking(BookingResponseDTO bookingResponse){
        // neu don hang khong ton tai
        if(!bookingRepository.existsById(bookingResponse.getId())) return;
        //lay danh sach ban co the dat duoc(state != 0) va cap nhat trang thai cua ban, va xoa cac ban khong ton tai
        List<BookedTableResponseDTO> bookedTableSuccess = bookingResponse.getBookedTables().stream()
            .filter(t->bookedTableService.existsById(t.getId()))//lay tat ca ca ban dat ton tai
            .peek((t)->{
                if(t.getState()==ApplicationConfig.NOT_FOUND_STATE){//ban khong ton tai
                    bookedTableService.delete(t.getId());
                }else{//ban ton tai
                    BookedTable bookedTable = new BookedTable();
                    BeanUtils.copyProperties(t, bookedTable);
                    bookedTableService.update(bookedTable);
                }
            })
            .filter(t->t.getState()!=ApplicationConfig.NOT_FOUND_STATE)//lay cac ban co the dat dau khi cap nhat
            .collect(Collectors.toList());
        bookingResponse.setDeposits(getDepositsBooking(bookingResponse));
        if(bookedTableSuccess==null || bookedTableSuccess.size()<=0)
            bookingRepository.deleteById(bookingResponse.getId());
        else{
            bookingRepository.updateState(bookingResponse.getId(), ApplicationConfig.WATING_STATE);
            bookingRepository.updateDeposits(bookingResponse.getId(), bookingResponse.getDeposits());
        }
        // if order error
        // bookingKafkaTemplate.send("notiOrder",bookingResponse);
    }
    private float getDepositsBooking(BookingResponseDTO bookingResponseDTO){
        return 100000;
    }

    @Transactional
    public void addDishToBooking(Long bookingId, List<BookedDish> dishs) throws Exception{
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if(booking.getState()==ApplicationConfig.DEFAULT_STATE)
            throw new Exception("Phiếu đặt bàn đang được xử lý, vui lòng đặt món sau khi xử lý xong");
        for(BookedDish bd: dishs){//set booking for dish
            bd.setBooking(booking);
            // dish was placed on the menu
            if(booking.getDishs().contains(bd)) throw new Exception(bd.getName() + "Đã có trong thực đơn");
        }
        bookedDishRepository.saveAll(dishs);
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());//get id user from access_token
        cartService.deleteAllDish(id);//delete all dish in cart of user
        // send message to check
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setId(bookingId.longValue());
        bookingResponseDTO.setDishs(new ArrayList<>());
        dishs.forEach((bd)->{
            BookedDishResponseDTO bdResp = new BookedDishResponseDTO();
            BeanUtils.copyProperties(bd, bdResp);
            bookingResponseDTO.getDishs().add(bdResp);
        });
//        bookingKafkaTemplate.send("checkBookedDish",bookingResponseDTO);
    }

    @Transactional
    public void checkAndUpdateDish(Booking booking){
        // neu don hang khong ton tai
        if(!bookingRepository.existsById(booking.getId())) return;
        booking.getDishs().stream().forEach((bd)->{
            if(bookedDishRepository.existsById(bd.getId())){// if dish exist
                if(bd.getState()==ApplicationConfig.NOT_FOUND_STATE)//delete dish not found
                    bookedDishRepository.deleteById(bd.getId());
                else bookedDishService.update(bd);// update full info dish
            }
        });
    }


    // EMPLOYEE
    public List<Booking> getAllByFromAndTo(Date from, Date to, Pageable pageable){
        List<Booking> list = bookingRepository.findAllByFromAndTo(from, to, pageable).toList();
        return list;
    }

    @Transactional
    public void cancelBooking(Long bookingId) throws Exception{
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if(booking.getState()!=ApplicationConfig.CANCEL_STATE)
            throw new Exception("Phiếu đã được đóng");
        bookedDishRepository.deleteAll(booking.getDishs());
        booking.getBookedTables().stream().forEach((t)->{
            // update state booked 
            t.setState(ApplicationConfig.CANCEL_STATE);
            bookedTableService.update(t);
        });
        bookingRepository.updateState(bookingId, ApplicationConfig.CANCEL_STATE);
    }

    @Transactional
    public void confirmBooking(Long bookingId) throws Exception{
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        //If the order is not check order
        if(booking.getState()==ApplicationConfig.DEFAULT_STATE)
            throw new Exception("Hệ thống đang kiểm tra phiếu đặt này");
        //If the order is not waiting
        if(booking.getState()!=ApplicationConfig.WATING_STATE)
            throw new Exception("Phiếu đã được xử lý");
        
        // update state booked table
        booking.getBookedTables().stream().forEach((t)->{
            t.setState(ApplicationConfig.OK_STATE);
            bookedTableService.update(t);
        });
        // update state booked dish
        if(!booking.getDishs().isEmpty()){
            booking.getDishs().stream().forEach((d)->{
                d.setState(ApplicationConfig.OK_STATE);
                bookedDishService.update(d);
            });
        }
        bookingRepository.updateState(bookingId, ApplicationConfig.OK_STATE);

        // update success
        BookingResponseDTO bkResp = new BookingResponseDTO();
        BeanUtils.copyProperties(booking, bkResp);
        // table
        bkResp.setBookedTables(new ArrayList<>());
        booking.getBookedTables().forEach((t)->{
            BookedTableResponseDTO tResp = new BookedTableResponseDTO();
            BeanUtils.copyProperties(t, tResp);
            bkResp.getBookedTables().add(tResp);
        });
        // dish
        if(!booking.getDishs().isEmpty()){
            booking.getDishs().forEach((d)->{
                BookedDishResponseDTO dResp = new BookedDishResponseDTO();
                BeanUtils.copyProperties(d, dResp);
                bkResp.getDishs().add(dResp);
            });
        }
//        bookingKafkaTemplate.send("createPaymentOrder",bkResp);//send info order need payment
//        bookingKafkaTemplate.send("notiOrder",bkResp);//send notification order success, required payment
    }

    @Transactional
    public int updatePaid(Long bookingId, boolean paid){
        return bookingRepository.updatePaid(bookingId, paid);
    }
}
