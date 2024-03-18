package com.dattp.order.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class BookingDishRequestDTO {
    @NotNull(message = "Mã phiếu không được để trống")
    private Long id;

    @NotEmpty(message = "Số lượng món ăn phải lớn hơn 0")
    @Valid
    private List<BookedDishRequestDTO> dishs;
}
