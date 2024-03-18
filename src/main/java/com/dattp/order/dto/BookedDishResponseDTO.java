package com.dattp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedDishResponseDTO {
    private int state;
    private long id;
    private long dishId;
    private int total;
    private float price;
    private String name;
    public BookedDishResponseDTO() {
    }
    @Override
    public boolean equals(Object obj) {
        BookedDishResponseDTO other = (BookedDishResponseDTO) obj;
        return id == other.id;
    }
}