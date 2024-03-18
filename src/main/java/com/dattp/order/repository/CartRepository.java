package com.dattp.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dattp.order.entity.Cart;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart,Long>{
    Optional<Cart> findByUserId(Long userId);
}