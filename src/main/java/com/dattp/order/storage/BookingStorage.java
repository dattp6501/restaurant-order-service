package com.dattp.order.storage;

import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookingStorage extends Storage{
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Booking save(Booking bk){
    return bookingRepository.save(bk);
  }

  public Booking findById(Long id){
    return bookingRepository.findById(id).orElseThrow(()-> new BadRequestException("Booking id = "+id+" Not found"));
  }
}