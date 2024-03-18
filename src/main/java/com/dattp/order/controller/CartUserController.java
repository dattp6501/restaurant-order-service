package com.dattp.order.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.CartResponseDTO;
import com.dattp.order.dto.DishInCartRequestDTO;
import com.dattp.order.dto.DishInCartResponseDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.dto.TableInCartRequestDTO;
import com.dattp.order.dto.TableInCartResponseDTO;
import com.dattp.order.entity.Cart;
import com.dattp.order.entity.DishInCart;
import com.dattp.order.entity.TableInCart;
import com.dattp.order.service.CartService;


@RestController
@RequestMapping("/api/order/user/cart")
public class CartUserController {
    @Autowired
    private CartService cartService;



    @GetMapping("/get_cart")
    public ResponseEntity<ResponseDTO> getCart() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartService.getById(id);
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        BeanUtils.copyProperties(cart, cartResponseDTO);
        // table
        if(!cart.getTables().isEmpty()){
            cartResponseDTO.setTables(new ArrayList<>());
            cart.getTables().forEach((t)->{
                TableInCartResponseDTO tableInCartResponseDTO = new TableInCartResponseDTO();
                BeanUtils.copyProperties(t, tableInCartResponseDTO);
                cartResponseDTO.getTables().add(tableInCartResponseDTO);
            });
        }
        // dish
        if(!cart.getDishs().isEmpty()){
            cartResponseDTO.setDishs(new ArrayList<>());
            cart.getDishs().forEach((d)->{
                DishInCartResponseDTO dishInCartResponseDTO = new DishInCartResponseDTO();
                BeanUtils.copyProperties(d, dishInCartResponseDTO);
                cartResponseDTO.getDishs().add(dishInCartResponseDTO);
            });
        }
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                cartResponseDTO
            )
        );
    }
    

    // table
    @PostMapping
    @RequestMapping("/add_table_to_cart")
    public ResponseEntity<ResponseDTO> addTableToCart(@RequestBody @Valid TableInCartRequestDTO req) throws Exception{
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        TableInCart tableInCart = new TableInCart();
        BeanUtils.copyProperties(req, tableInCart);
        cartService.addTable(id, tableInCart);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }

    @PostMapping
    @RequestMapping("/delete_table_in_cart")
    public ResponseEntity<ResponseDTO> deleteTableInCart(@RequestBody @Valid TableInCartRequestDTO req){
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        cartService.deleteTableInCart(id, req.getTableId());
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }

    // dish
    @PostMapping
    @RequestMapping("/add_dish_to_cart")
    public ResponseEntity<ResponseDTO> addDishToCart(@RequestBody @Valid DishInCartRequestDTO req) throws Exception{
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        DishInCart dishInCart = new DishInCart();
        BeanUtils.copyProperties(req, dishInCart);
        cartService.addDishInCart(id, dishInCart);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }
}
