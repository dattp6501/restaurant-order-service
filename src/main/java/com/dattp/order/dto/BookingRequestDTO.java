package com.dattp.order.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter
public class BookingRequestDTO {
    // private int state;
    // private long id;
    // private long customerId;

    @NotNull(message = "Tên người đặt(custemerFullname) không được để trống")
    private String custemerFullname;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    @NotNull(message = "Thời gian bắt đầu(from) không được bỏ trống")
    private Date from;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    @NotNull(message = "Thời gian kết thúc(to) không được bỏ trống")
    private Date to;

    @NotNull(message = "Phải có ít nhất 1 bàn được đặt")
    @Size(min = 1, message = "Phải có ít nhất 1 bàn được đặt")
    @Valid// kiểm tra các phần tử bên trong danh sách
    @JsonProperty("tables")
    private List<BookedTableRequestDTO> bookedTables;

    @Valid
    private List<BookedDishRequestDTO> dishs;

    private String description;

}