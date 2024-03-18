package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishInCartResponseDTO {
    private Long id;
    private Long dishId;
    private String name;
    private float price;
    private int total;
    public DishInCartResponseDTO() {
    }
}
