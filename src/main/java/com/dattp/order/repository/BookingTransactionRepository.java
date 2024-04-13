package com.dattp.order.repository;

import com.dattp.order.entity.BookingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTransactionRepository extends JpaRepository<BookingTransaction, Long> {
}
