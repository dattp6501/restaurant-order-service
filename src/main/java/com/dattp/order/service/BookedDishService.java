package com.dattp.order.service;

import com.dattp.order.storage.BookedDishStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookedDishService extends com.dattp.order.service.Service {
  @Autowired
  private BookedDishStorage bookedDishStorage;
//    public void removeById(long id){
//        bookedDishRepository.deleteById(id);
//    }
//
//    public boolean existsById(long id){
//        return bookedDishRepository.existsById(id);
//    }
//
//    public void saveAll(List<BookedDish> list){
//        bookedDishRepository.saveAll(list);
//    }

//    @Transactional
//    public void update(BookedDish bookedDish) {
//        BookedDish bookedDishSrc = bookedDishStorage.findById(bookedDish.getId());
//        if (!bookedDish.getName().isEmpty()) bookedDishSrc.setName(bookedDish.getName());
//        if (bookedDish.getPrice() > 0) bookedDishSrc.setPrice(bookedDish.getPrice());
//        if (bookedDish.getTotal() > 0) bookedDishSrc.setTotal(bookedDish.getTotal());
//        bookedDishSrc.setState(bookedDish.getState());
//        bookedDishStorage.save(bookedDishSrc);
//    }
}
