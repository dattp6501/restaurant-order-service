package com.dattp.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class BookedDishRequestDTO {
    // private int state;
    // private long id;
    
    @Min(value = 1, message = "Bạn chưa chọn món(dish_id) hoặc món không tồn tại")
    @JsonProperty("dish_id")
    private long dishId;

    @Min(value = 1, message = "Số lướng món(total) phải lớn hơn 0")
    private int total;

    @NotEmpty(message = "Tên món ắn(name) không được để trống")
    private String name;

    // @Min(value = 1, message = "Giá món(price) phải lớn hơn 0")
    // private float price;
}