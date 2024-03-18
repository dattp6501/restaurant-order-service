package com.dattp.order.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{
    @Modifying
    @Query(value="UPDATE BOOKING bk SET bk.deposits = :deposits WHERE bk.id = :id", nativeQuery = true)
    public int updateDeposits(@Param("id") Long id,@Param("deposits") float deposits);

    @Modifying
    @Query(value="UPDATE BOOKING bk SET bk.paid = :paid WHERE bk.id = :id", nativeQuery = true)
    public int updatePaid(@Param("id") Long id,@Param("paid") boolean paid);

    @Modifying
    @Query(value="UPDATE BOOKING bk SET bk.state = :state WHERE bk.id = :id", nativeQuery = true)
    public int updateState(@Param("id") Long id,@Param("state") Integer state);

    @Query(value="SELECT * FROM BOOKING bk WHERE bk.customer_id=:customer_id ORDER BY bk.date", nativeQuery = true)
    public Page<Booking> getAllByCustomerId(@Param("customer_id") Long customerId, Pageable pageable);

    @Query(
        value = "SELECT * FROM BOOKING b WHERE :from_<=b.from_ AND b.to_<=:to_",
        nativeQuery = true
    )
    Page<Booking> findAllByFromAndTo(@Param("from_") Date from, @Param("to_") Date to, Pageable pageable);
}