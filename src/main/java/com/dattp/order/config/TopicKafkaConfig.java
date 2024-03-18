//package com.dattp.order.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class TopicKafkaConfig {
//    // booking
//    @Bean
//    public NewTopic createBookingTopic(){//check info booking
//        return TopicBuilder.name("newOrder").build();
//    }
//
//    @Bean
//    public NewTopic resultCheckBookingTopic(){//result check info booking
//        return TopicBuilder.name("checkOrder").build();
//    }
//    // dish
//    @Bean
//    public NewTopic checkBookedDishTopic(){//check info booked dish
//        return TopicBuilder.name("checkBookedDish").build();
//    }
//
//    @Bean
//    public NewTopic resultCheckBookedDishTopic(){//result check info booked dish
//        return TopicBuilder.name("resultCheckBookedDish").build();
//    }
//
//    // payment
//    @Bean
//    public NewTopic createPaymentOrderTopic(){//save info payment
//        return TopicBuilder.name("createPaymentOrder").build();
//    }
//
//    @Bean
//    public NewTopic paymentOrderSuccessTopic(){//update info booking
//        return TopicBuilder.name("paymentOrderSuccess").build();
//    }
//
//    @Bean
//    public NewTopic notificationOrderTopic(){
//        return TopicBuilder.name("notiOrder").build();
//    }
//
//    @Bean
//    public NewTopic newUserTopic(){
//        return TopicBuilder.name("newUser").build();
//    }
//}