package com.dattp.order.storage;

import com.dattp.order.entity.BookedDish;
import com.dattp.order.repository.BookedDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookedDishStorage extends Storage {
  @Autowired
  private BookedDishRepository bookedDishRepository;

  public BookedDish save(BookedDish bookedDish) {
    return bookedDishRepository.save(bookedDish);
  }

  public BookedDish findById(Long id) {
    return bookedDishRepository.findById(id).orElseThrow(null);
  }
}