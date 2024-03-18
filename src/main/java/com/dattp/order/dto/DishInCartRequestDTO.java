package com.dattp.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishInCartRequestDTO {
    @JsonProperty("dishId")
    @NotNull(message="dishId không được để trống")
    private Long dishId;

    @NotNull(message="Tên món(name) không được để trống")
    private String name;

    @Min(value = 1, message = "GIá món phải > 0")
    private float price;

    @Min(value = 1, message = "Số lượng đặt(total) phải > 0")
    private int total;
}
