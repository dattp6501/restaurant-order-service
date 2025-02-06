package com.dattp.order.dto.bookedtable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class BookedTableCreateDTO {
  @Min(value = 1, message = "Bàn(id) chưa được chọn or không tồn tại")
  @JsonProperty("id")
  private Long tableId;

  @NotNull(message = "Tên bàn(name) không được để trống")
  private String name;

  @NotNull(message = "price không được để trống")
  private Float price;

  @NotNull(message = "image không được để trống")
  private String image;
}
