package com.dattp.order.storage;

import com.dattp.order.entity.BookingTransaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookingTransactionStorage extends Storage {
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.MANDATORY)
  public void save(BookingTransaction bookingTransaction) {
    bookingTransactionRepository.save(bookingTransaction);
  }
}