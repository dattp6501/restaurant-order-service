package com.dattp.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class BookedTableRequestDTO {
    @Min(value = 1, message = "Bàn(tableId) chưa được chọn or không tồn tại")  
    @JsonProperty("tableId")  
    private long tableId;

    @NotNull(message = "Tên bàn(name) không được để trống")
    private String name;
}
