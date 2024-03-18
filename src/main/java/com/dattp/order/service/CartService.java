package com.dattp.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dattp.order.entity.Cart;
import com.dattp.order.entity.DishInCart;
import com.dattp.order.entity.TableInCart;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.repository.CartRepository;
import com.dattp.order.repository.DishInCartRepository;
import com.dattp.order.repository.TableInCartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TableInCartRepository tableInCartRepository;

    @Autowired
    private DishInCartRepository dishInCartRepository;

    public Cart createCart(Cart cart){
        Cart cartSrc = cartRepository.findByUserId(cart.getUserId()).orElse(null);
        if(cartSrc != null) return cartSrc;
        return cartRepository.save(cart); 
    }

    public Cart getById(Long userId){
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        return cart;
    }

    @Transactional
    public void addTable(Long userId, TableInCart tableInCart) throws Exception{
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if(cart==null) throw new Exception("Giỏ hàng của người dùng không tồn tại");
        List<Object[]> list = tableInCartRepository.findByTableIdAndCartId(cart.getId(), tableInCart.getTableId());
        if(!list.isEmpty()) 
            throw new BadRequestException("Bàn đã tồn tại trong danh sách");
        tableInCart.setCart(cart);
        tableInCartRepository.save(tableInCart);
    }

    @Transactional
    public int deleteTableInCart(Long userId, Long tableId){
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        return tableInCartRepository.deleteByTableIdAndCartId(cart.getId(), tableId);
    }

    @Transactional
    public void deleteAllTable(Long userId){
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        tableInCartRepository.deleteAllTableByCartId(cart.getId());
    }

    // dish
    @Transactional
    public void addDishInCart(Long userId, DishInCart dishInCart) throws Exception{
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if(cart==null) throw new Exception("Giỏ hàng của người dùng không tồn tại");
        // check dish in cart
        List<Object[]> list = dishInCartRepository.findByDishIdAndCartId(cart.getId(), dishInCart.getDishId());
        if(!list.isEmpty())
            throw new BadRequestException("Món ăn đã được thêm vào danh sách");
        dishInCart.setCart(cart);
        dishInCartRepository.save(dishInCart);
    }

    @Transactional
    public void deleteDishInCart(Long dishInCartId){
        dishInCartRepository.deleteById(dishInCartId);
    }

    @Transactional
    public void deleteAllDish(Long userId) throws Exception{
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if(cart==null) throw new Exception("Giỏ hàng của người dùng không tồn tại");
        dishInCartRepository.deleteAllDishByCartId(cart.getId());
    }
}