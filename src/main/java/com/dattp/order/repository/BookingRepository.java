package com.dattp.order.repository;

import com.dattp.order.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
  @Query(value = "SELECT id FROM booking " +
      "WHERE create_at<= :createdAt AND deposits<=0 AND state != :state", nativeQuery = true)
  List<Long> findAllNotPaid(@Param("createdAt") Long createdAt, @Param("state") String state);


  @Modifying
  @Query(value = "UPDATE booking bk SET bk.deposits = :deposits WHERE bk.id = :id", nativeQuery = true)
  int updateDeposits(@Param("id") Long id, @Param("deposits") float deposits);

  @Modifying
  @Query(value = "UPDATE booking bk SET bk.paid = :paid WHERE bk.id = :id", nativeQuery = true)
  int updatePaid(@Param("id") Long id, @Param("paid") boolean paid);

  @Modifying
  @Query(value = "UPDATE booking bk SET bk.state = :state WHERE bk.id = :id", nativeQuery = true)
  int updateState(@Param("id") Long id, @Param("state") Integer state);

  @Query(value = "SELECT * FROM booking bk WHERE bk.customer_id=:customer_id ORDER BY bk.date", nativeQuery = true)
  Page<Booking> getAllByCustomerId(@Param("customer_id") Long customerId, Pageable pageable);

  @Query(
      value = "SELECT * FROM booking b WHERE :from_<=b.from_ AND b.to_<=:to_",
      nativeQuery = true
  )
  Page<Booking> findAllByFromAndTo(@Param("from_") Date from, @Param("to_") Date to, Pageable pageable);
}