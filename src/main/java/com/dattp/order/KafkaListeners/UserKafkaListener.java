//package com.dattp.order.KafkaListeners;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.dattp.order.dto.kafka.UserKafka;
//import com.dattp.order.entity.Cart;
//import com.dattp.order.service.CartService;
//
//@Component
//public class UserKafkaListener {
//    @Autowired
//    private CartService cartService;
//
//    @KafkaListener(topics = "new-user", groupId="group2", containerFactory = "factoryUser")
//    @Transactional
//    public void listenerResultCreateBookingTopic(@Payload UserKafka user){
//        cartService.createCart(new Cart(user.getId()));
//    }
//}
