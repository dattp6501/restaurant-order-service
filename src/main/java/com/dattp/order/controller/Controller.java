package com.dattp.order.controller;

import com.dattp.order.service.BookedTableService;
import com.dattp.order.service.BookingService;
import com.dattp.order.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@org.springframework.stereotype.Controller
public class Controller {
  @Autowired
  @Lazy
  protected JWTService jwtService;

  @Autowired
  @Lazy
  protected BookingService bookingService;
  @Autowired
  @Lazy
  protected BookedTableService bookedTableService;
}