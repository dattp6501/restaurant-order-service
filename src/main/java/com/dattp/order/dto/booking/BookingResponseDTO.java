package com.dattp.order.dto.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.dattp.order.dto.bookeddish.BookedDishResponseDTO;
import com.dattp.order.dto.bookedtable.BookedTableResponseDTO;
import com.dattp.order.entity.Booking;
import com.dattp.order.entity.state.BookingState;
import com.dattp.order.pojo.booking.BookingOverview;
import com.dattp.order.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class BookingResponseDTO {
  private Long id;

  private Long CustomerId;

  private String custemerFullname;

  private BookingState state;

  private Boolean paid;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private LocalDateTime from;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private LocalDateTime to;

  private Float deposits;

  private String description;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private LocalDateTime createAt;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private LocalDateTime updateAt;

  private List<BookedTableResponseDTO> bookedTables;

  private List<BookedDishResponseDTO> dishs;

  public BookingResponseDTO(){super();}

  public BookingResponseDTO(Booking bk){
    copyProperties(bk);
  }

  public void copyProperties(Booking bk){
    BeanUtils.copyProperties(bk, this);
    this.from = DateUtils.convertToLocalDateTime(bk.getFrom());
    this.to = DateUtils.convertToLocalDateTime(bk.getTo());
    this.createAt = DateUtils.convertToLocalDateTime(bk.getCreateAt());
    this.updateAt = DateUtils.convertToLocalDateTime(bk.getUpdateAt());
    //booked table
    if(Objects.nonNull(bk.getBookedTables())){
      this.bookedTables = bk.getBookedTables().stream()
          .map(BookedTableResponseDTO::new)
          .collect(Collectors.toList());
    }
    //booked dish
    if(Objects.nonNull(bk.getDishs())){
      this.dishs = bk.getDishs().stream()
          .map(BookedDishResponseDTO::new)
          .collect(Collectors.toList());
    }
  }

  public BookingResponseDTO(BookingOverview bo){
    copyProperties(bo);
  }
  public void copyProperties(BookingOverview bo){
    BeanUtils.copyProperties(bo, this);
    this.from = DateUtils.convertToLocalDateTime(bo.getFrom());
    this.to = DateUtils.convertToLocalDateTime(bo.getTo());
    this.createAt = DateUtils.convertToLocalDateTime(bo.getCreateAt());
  }
}