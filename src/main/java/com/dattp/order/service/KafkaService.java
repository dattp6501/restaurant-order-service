package com.dattp.order.service;

import com.dattp.order.dto.booking.BookingResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaService {
    @Autowired @Lazy private KafkaTemplate<String, BookingResponseDTO> kafkaTemplateBooking;

    @Async("taskExecutor")
    public void send(String topic, Object data){
        try {
            if(data instanceof BookingResponseDTO) kafkaTemplateBooking.send(topic, (BookingResponseDTO) data);



            else log.debug("======> KafkaService::send::{}::{} NOT CONFIG SEND KAFKA!!!", topic, data);
            log.debug("======> KafkaService::send::{}::{} SUCCESS!!!", topic, data);
        }catch (Exception e){
            log.debug("======> KafkaService::send::{}::{}::Exception:{}",topic, data, e.getMessage());
        }
    }
}
