package com.dattp.order.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDTO {
    private long id;

    private long CustomerId;

    private String custemerFullname;

    private int state;

    private boolean paid;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;

    private float deposits;

    private String description;

    private List<BookedTableResponseDTO> bookedTables;

    private List<BookedDishResponseDTO> dishs;
    
    public BookingResponseDTO(){}
}