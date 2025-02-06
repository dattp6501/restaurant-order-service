package com.dattp.order.schedule;

import com.dattp.order.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSchedule {
  @Autowired
  private BookingService bookingService;

  //    @Scheduled(initialDelay = 1000, fixedDelay = 1*60*1000)
  public void processOrderNotPaid() {
    bookingService.processOrderNotPaid();
  }
}
