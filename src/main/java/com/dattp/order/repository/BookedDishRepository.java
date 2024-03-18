package com.dattp.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dattp.order.entity.BookedDish;

public interface BookedDishRepository extends JpaRepository<BookedDish,Long>{
}
