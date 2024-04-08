package com.dattp.order.dto.bookeddish;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedDishRequestDTO {
    
    @Min(value = 1, message = "Bạn chưa chọn món(dish_id) hoặc món không tồn tại")
    @JsonProperty("id")
    private long dishId;

    @Min(value = 1, message = "Số lướng món(total) phải lớn hơn 0")
    private int total;

//    @NotEmpty(message = "Tên món ắn(name) không được để trống")
    private String name;
}