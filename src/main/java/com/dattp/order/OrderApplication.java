package com.dattp.order;

import com.dattp.order.service.TelegramService;
import com.dattp.order.utils.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
@EnableScheduling
public class OrderApplication {
  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }

  @Bean
  public CommandLineRunner CommandLineRunnerBean(TelegramService telegramService) {
    return (args) -> {
      String message =
          DateUtils.getcurrentLocalDateTime()
              .plusHours(7)
              .format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd"))
              + ": ORDER ===> STARTED";
      telegramService.sendNotificatonMonitorSystem(message);
    };
  }
}