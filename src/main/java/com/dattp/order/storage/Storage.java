package com.dattp.order.storage;

import com.dattp.order.repository.BookedDishRepository;
import com.dattp.order.repository.BookingRepository;
import com.dattp.order.repository.BookingTransactionRepository;
import com.dattp.order.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Storage {
  @Autowired @Lazy protected RedisService redisService;

  @Autowired @Lazy protected BookingTransactionStorage bookingTransactionStorage;

  @Autowired @Lazy protected BookingRepository bookingRepository;
  @Autowired @Lazy protected BookedDishRepository bookedDishRepository;
  @Autowired @Lazy protected BookingTransactionRepository bookingTransactionRepository;
}
