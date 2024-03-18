package com.dattp.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableInCartRequestDTO {
    @JsonProperty("tableId")
    @NotNull(message="tableId không được để trống")
    private Long tableId;

    @NotNull(message="Tên bàn(name) không được để trống")
    private String name;

    @Min(value = 1, message = "GIá thuê bàn phải > 0")
    private float price;
}
