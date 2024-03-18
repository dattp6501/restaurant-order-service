package com.dattp.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.entity.TableInCart;

import java.util.List;


public interface TableInCartRepository extends JpaRepository<TableInCart,Long>{
    @Query(
        value = "SELECT t.id FROM TABLE_IN_CART t "
        +"WHERE t.cart_id=:cart_id AND t.table_id=:table_id ", nativeQuery = true
    )
    public List<Object[]> findByTableIdAndCartId(@Param("cart_id") Long cartId, @Param("table_id") Long tableId);

    @Modifying
    @Query(
        value = "DELETE FROM TABLE_IN_CART t "
        +"WHERE t.cart_id=:cart_id AND t.table_id=:table_id ", nativeQuery = true
    )
    public int deleteByTableIdAndCartId(@Param("cart_id") Long cartId, @Param("table_id") Long tableId);

    @Modifying
    @Query(
        value = "DELETE FROM TABLE_IN_CART t "
        +"WHERE t.cart_id=:cart_id ", nativeQuery = true
    )
    public int deleteAllTableByCartId(@Param("cart_id") Long cartId);
}