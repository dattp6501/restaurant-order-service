package com.dattp.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private Long userId;
    private List<TableInCartResponseDTO> tables;
    private List<DishInCartResponseDTO> dishs;
    public CartResponseDTO() {
    }
}