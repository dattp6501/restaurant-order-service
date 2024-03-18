//package com.dattp.order.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.LongDeserializer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import com.dattp.order.dto.BookingResponseDTO;
//import com.dattp.order.dto.kafka.UserKafka;
//@EnableKafka
//@Configuration
//public class ComsumerKafkaConfig {
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServer;
//
//    public Map<String, Object> comsumerConfigJSON(){
//        Map<String,Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        return props;
//    }
//    // result check booking
//    @Bean
//    public ConsumerFactory<String,BookingResponseDTO> consumerFactoryBooking(){
//        JsonDeserializer<BookingResponseDTO> jsonDeserializer = new JsonDeserializer<>(BookingResponseDTO.class,false);
//        jsonDeserializer.addTrustedPackages("*");
//        return new DefaultKafkaConsumerFactory<>(comsumerConfigJSON(),new StringDeserializer(), jsonDeserializer);
//    }
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,BookingResponseDTO>> factoryBooking(ConsumerFactory<String,BookingResponseDTO> consumerFactoryBooking){
//        ConcurrentKafkaListenerContainerFactory<String,BookingResponseDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryBooking);
//        return factory;
//    }
//    // user
//    @Bean
//    public ConsumerFactory<String,UserKafka> consumerFactoryUser(){
//        JsonDeserializer<UserKafka> jsonDeserializer = new JsonDeserializer<>(UserKafka.class,false);
//        jsonDeserializer.addTrustedPackages("*");
//        return new DefaultKafkaConsumerFactory<>(comsumerConfigJSON(),new StringDeserializer(), jsonDeserializer);
//    }
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,UserKafka>> factoryUser(ConsumerFactory<String,UserKafka> consumerFactoryUser){
//        ConcurrentKafkaListenerContainerFactory<String,UserKafka> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryUser);
//        return factory;
//    }
//    // string
//    public Map<String, Object> comsumerConfigString(){
//        Map<String,Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return props;
//    }
//    @Bean
//    public ConsumerFactory<String,String> consumerFactoryString(){
//        return new DefaultKafkaConsumerFactory<>(comsumerConfigString());
//    }
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> factoryString(ConsumerFactory<String,String> consumerFactoryString){
//        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryString);
//        return factory;
//    }
//    // long
//    public Map<String, Object> comsumerLongConfig(){
//        Map<String,Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
//        return props;
//    }
//    @Bean
//    public ConsumerFactory<String,Long> consumerLongFactory(){
//        return new DefaultKafkaConsumerFactory<>(comsumerLongConfig());
//    }
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,Long>> factoryLong(ConsumerFactory<String,Long> consumerFactory){
//        ConcurrentKafkaListenerContainerFactory<String,Long> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
//}