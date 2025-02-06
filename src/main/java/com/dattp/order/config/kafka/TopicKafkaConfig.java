package com.dattp.order.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {
  public static final String NEW_BOOKING_TOPIC = "com.dattp.restaurant.order.new_order";
  public static final String PROCESS_BOOKING_TOPIC = "com.dattp.restaurant.order.process_order";
  public static final String CONFIRM_BOOKING_TOPIC = "com.dattp.restaurant.order.confirm_order";
  public static final String CANCEL_BOOKING_TOPIC = "com.dattp.restaurant.order.cancel_order";
  @Value("${kafka.partition}")
  public int PARTITION;

  // booking
  @Bean
  public NewTopic createBookingTopic() {//check info booking
    return TopicBuilder
        .name(NEW_BOOKING_TOPIC)
        .partitions(PARTITION)
        .build();
  }

  @Bean
  public NewTopic confirmBookingTopic() {//check info booking
    return TopicBuilder
        .name(CONFIRM_BOOKING_TOPIC)
        .partitions(PARTITION)
        .build();
  }

  @Bean
  public NewTopic cancalBookingTopic() {//check info booking
    return TopicBuilder
        .name(CANCEL_BOOKING_TOPIC)
        .partitions(PARTITION)
        .build();
  }
}