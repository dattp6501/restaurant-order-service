package com.dattp.order.config.kafka;

import com.dattp.order.dto.booking.BookingResponseDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class ProducerKafkaConfig {
  @Value("${spring.kafka.bootstrap-servers}")
  private String BOOTSTRAP_SERVER;

//  @Value("${kafka.test}")
//  private boolean KAFKA_TEST;

//  @Value("${spring.kafka.properties.sasl.jaas.config}")
//  private String SASL_JAAS_CONFIG;
//  @Value("${spring.kafka.properties.sasl.mechanism}")
//  private String SASL_MECHANISM;
//  @Value("${spring.kafka.properties.security.protocol}")
//  private String SECURITY_PROTOCOL;
//  @Value("${spring.kafka.properties.sasl.trust_store_password}")
//  private String TRUST_STORE_PASSWORD;

  public Map<String, Object> producerConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "com.dattp.restaurant.order.id");
    props.put(ProducerConfig.ACKS_CONFIG, "1");

//    if(KAFKA_TEST){
//      props.put("sasl.jaas.config", SASL_JAAS_CONFIG);
//      props.put("sasl.mechanism", SASL_MECHANISM);
//      props.put("security.protocol", SECURITY_PROTOCOL);
//      props.put("ssl.endpoint.identification.algorithm", "");
//      props.put("ssl.truststore.type", "jks");
//      props.put("ssl.truststore.location", Objects.requireNonNull(getClass().getClassLoader().getResource("client.truststore.jks")).getPath());
//      props.put("ssl.truststore.password", TRUST_STORE_PASSWORD);
//    }
    return props;
  }

  // producer example
  @Bean
  public ProducerFactory<String, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfig());
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplateString(ProducerFactory<String, String> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  // producer booking
  @Bean
  public ProducerFactory<String, BookingResponseDTO> producerFactoryBooking() {
    return new DefaultKafkaProducerFactory<>(producerConfig());
  }

  @Bean
  public KafkaTemplate<String, BookingResponseDTO> kafkaTemplateBooking(ProducerFactory<String, BookingResponseDTO> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }
}
