package com.dattp.order.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.config.ApplicationConfig;
import com.dattp.order.entity.BookedTable;

public interface BookedTableRepository extends JpaRepository<BookedTable,Long>{
    // lay tat cac ban dat trong 1 khoang thoi gian(1 ban co the dat nhieu lan trong khoang thoi gian khac nhau)
    @Query(value="SELECT t.table_id,t.from_,t.to_ FROM BOOKED_TABLE t "
    +"WHERE NOT (:to_<t.from_ OR t.to_<:from_) "+" AND t.state!="+ApplicationConfig.CANCEL_STATE+" ORDER BY t.table_id", nativeQuery = true)
    public Page<Object[]> findPeriadrentAllTable(@Param("from_") Date from, @Param("to_")Date to, Pageable pageable);

    // lay tat cac thoi gian dat trong 1 khoang thoi gian cua 1 ban(hay lay cac khung thoi gian da dat cua ban)
    @Query(value="SELECT t.from_,t.to_ FROM BOOKED_TABLE t "
    +"WHERE NOT (:to_<t.from_ OR t.to_<:from_) AND t.table_id=:table_id "+" AND t.state!="+ApplicationConfig.CANCEL_STATE, nativeQuery = true)
    public List<Object[]> findPeriodRent(@Param("from_") Date from, @Param("to_")Date to, @Param("table_id") Long tableID);
}