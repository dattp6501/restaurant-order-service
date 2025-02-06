package com.dattp.order.dto.booking;

import com.dattp.order.dto.bookeddish.BookedDishRequestDTO;
import com.dattp.order.dto.bookedtable.BookedTableCreateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class BookingCreateDTO {
  @NotNull(message = "Tên người đặt(custemerFullname) không được để trống")
  private String custemerFullname;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  @NotNull(message = "Thời gian bắt đầu(from) không được bỏ trống")
  private LocalDateTime from;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  @NotNull(message = "Thời gian kết thúc(to) không được bỏ trống")
  private LocalDateTime to;

  @NotNull(message = "Phải có ít nhất 1 bàn được đặt")
  @Size(min = 1, message = "Phải có ít nhất 1 bàn được đặt")
  @Valid// kiểm tra các phần tử bên trong danh sách
  @JsonProperty("tables")
  private List<BookedTableCreateDTO> bookedTables;

  @Valid
  private List<BookedDishRequestDTO> dishs;

  private String description;

}