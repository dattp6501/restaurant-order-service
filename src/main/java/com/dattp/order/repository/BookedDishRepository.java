package com.dattp.order.repository;

import com.dattp.order.entity.BookedDish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedDishRepository extends JpaRepository<BookedDish, Long> {
}
