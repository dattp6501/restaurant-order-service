package com.dattp.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.order.entity.BookedDish;
import com.dattp.order.repository.BookedDishRepository;

@Service
public class BookedDishService {
    @Autowired
    private BookedDishRepository bookedDishRepository;

    public void removeById(long id){
        bookedDishRepository.deleteById(id);
    }

    public boolean existsById(long id){
        return bookedDishRepository.existsById(id);
    }

    public void saveAll(List<BookedDish> list){
        bookedDishRepository.saveAll(list);
    }

    @Transactional
    public void update(BookedDish bookedDish){
        BookedDish bookedDishSrc = bookedDishRepository.findById(bookedDish.getId()).orElseThrow();
        if(!bookedDish.getName().isEmpty()) bookedDishSrc.setName(bookedDish.getName());
        if(bookedDish.getPrice()>0) bookedDishSrc.setPrice(bookedDish.getPrice());
        if(bookedDish.getTotal()>0) bookedDishSrc.setTotal(bookedDish.getTotal());
        bookedDishSrc.setState(bookedDish.getState());
        bookedDishRepository.save(bookedDishSrc);
    }
}
