package com.dattp.order.storage;

import com.dattp.order.repository.BookingRepository;
import com.dattp.order.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Storage {
  @Autowired @Lazy protected RedisService redisService;

  @Autowired @Lazy protected BookingRepository bookingRepository;
}
