package com.dattp.order.KafkaListeners;

import com.dattp.order.config.kafka.TopicKafkaConfig;
import com.dattp.order.dto.booking.BookingResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;
@Component
@Log4j2
public class BookingKafkaListener extends com.dattp.order.KafkaListeners.KafkaListener{
    // listen process booking
    @KafkaListener(topics = TopicKafkaConfig.PROCESS_BOOKING_TOPIC, groupId="com.dattp.restaurant.order.process_order", containerFactory = "factoryBooking")
    public void listenerResultCreateBookingTopic(@Payload BookingResponseDTO dto, Acknowledgment acknowledgment){
        // ket qua nhan vao la thong tin cua ban sau khi productservice kiem tra
        try {
            bookingService.checkAndUpdateBooking(dto);
            acknowledgment.acknowledge();
        }catch (Exception e){
            log.debug("=====================>  listenerResultCreateBookingTopic:Exception:{}", e.getMessage());
        }

    }
    // listen message result check info dish
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
}