package com.dattp.order.KafkaListeners;

import com.dattp.order.service.BookingService;
import com.dattp.order.service.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {
  @Autowired
  @Lazy
  protected TelegramService telegramService;
  @Autowired
  @Lazy
  protected BookingService bookingService;
}
