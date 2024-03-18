//package com.dattp.order.KafkaListeners;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.dattp.order.dto.BookingResponseDTO;
//import com.dattp.order.entity.BookedDish;
//import com.dattp.order.entity.Booking;
//import com.dattp.order.service.BookingService;
//
//@Component
//public class BookingKafkaListener {
//    @Autowired
//    private BookingService bookingService;
//    // listen message from checkOrder topic
//    @KafkaListener(topics = "checkOrder", groupId="group1", containerFactory = "factoryBooking")
//    @Transactional
//    public void listenerResultCreateBookingTopic(@Payload BookingResponseDTO bookingResponse){
//        // ket qua nhan vao la thong tin cua ban sau khi productservice kiem tra
//        // lang nghe su kien kiem tra don dat hang
//        bookingService.checkAndUpdateBooking(bookingResponse);
//    }
//    // listen message result check info dish
//    @KafkaListener(topics = "resultCheckBookedDish", groupId = "group1", containerFactory = "factoryBooking")
//    @Transactional
//    public void listenerResultCheckBookedDish(BookingResponseDTO bookingResponse){
//        Booking booking = new Booking();
//        BeanUtils.copyProperties(bookingResponse, booking);
//        booking.setDishs(new ArrayList<>());
//        bookingResponse.getDishs().forEach((bdResp)->{
//            BookedDish bd = new BookedDish();
//            BeanUtils.copyProperties(bdResp, bd);
//            booking.getDishs().add(bd);
//        });
//        bookingService.checkAndUpdateDish(booking);
//    }
//    //
//    @KafkaListener(topics = "paymentOrderSuccess", groupId = "group1", containerFactory = "factoryLong")
//    @Transactional
//    public void listenerPaymentOrderSuccess(Long bookingId){
//        bookingService.updatePaid(bookingId, true);
//    }
//}