package com.dattp.order.pojo.booking;

import com.dattp.order.entity.Booking;
import com.dattp.order.entity.state.BookingState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class BookingOverview {
  private Long id;

  private BookingState state;

  private Long CustomerId;

  private String custemerFullname;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private Long from;

  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private Long to;

  private Float deposits;

  private Boolean paid;

  private String description;

  private Long createAt;

  public BookingOverview() {
    super();
  }

  public BookingOverview(Booking booking) {
    copyProperties(booking);
  }

  public void copyProperties(Booking booking) {
    BeanUtils.copyProperties(booking, this);
  }
}