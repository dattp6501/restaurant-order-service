package com.dattp.order.service;

import com.dattp.order.storage.BookingStorage;
import com.dattp.order.storage.BookingTransactionStorage;
import com.dattp.order.storage.TokenStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    @Lazy
    protected BookedTableService bookedTableService;
    @Autowired
    @Lazy
    protected BookedDishService bookedDishService;
    @Autowired
    @Lazy
    protected JWTService jwtService;
    @Autowired
    @Lazy
    protected KafkaService kafkaService;
    @Autowired
    @Lazy
    protected RestTemplate restTemplate;
    @Autowired
    @Lazy
    protected RedisService redisService;


    @Autowired
    @Lazy
    protected TokenStorage tokenStorage;
    @Autowired
    @Lazy
    protected BookingStorage bookingStorage;
    @Autowired
    @Lazy
    protected BookingTransactionStorage bookingTransactionStorage;

}
