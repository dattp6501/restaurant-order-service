package com.dattp.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.order.entity.DishInCart;

public interface DishInCartRepository extends JpaRepository<DishInCart,Long>{
    @Query(
        value = "SELECT d.id FROM DISH_IN_CART d "
        +"WHERE d.cart_id=:cart_id AND d.dish_id=:dish_id ", nativeQuery = true
    )
    public List<Object[]> findByDishIdAndCartId(@Param("cart_id") Long cartId, @Param("dish_id") Long dishId);

    @Modifying
    @Query(
        value = "DELETE FROM DISH_IN_CART d "
        +"WHERE d.cart_id=:cart_id ", nativeQuery = true
    )
    public int deleteAllDishByCartId(@Param("cart_id") Long cartId);
}