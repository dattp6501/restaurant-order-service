package com.dattp.order.entity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.dattp.order.dto.booking.BookingCreateDTO;
import com.dattp.order.dto.booking.BookingResponseDTO;
import com.dattp.order.entity.state.BookingState;
import com.dattp.order.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "BOOKING")
@Getter
@Setter
@AllArgsConstructor
public class Booking {
    @Column(name="id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private BookingState state;

    @Column(name = "customer_id")
    private Long CustomerId;

    @Column(name="custemer_fullname")
    private String custemerFullname;

    @Column(name = "from_")
    // @CreatedDate
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Long from;

    @Column(name = "to_")
    // @CreatedDate
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Long to;

    @Column(name = "deposits")
    private Float deposits;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "desciption")
    private String description;

    @Column(name = "create_at")
    private Long createAt = DateUtils.getCurrentMils();

    @Column(name = "update_at")
    private Long updateAt = DateUtils.getCurrentMils();

    @OneToMany(mappedBy = "booking", cascade ={CascadeType.ALL})
    private List<BookedTable> bookedTables;

    @OneToMany(mappedBy="booking", cascade={CascadeType.ALL})
    private List<BookedDish> dishs;

    public Booking(){
        super();
    }

    public Booking(BookingCreateDTO dto){
        copyProperties(dto);
    }

    public void copyProperties(BookingCreateDTO dto){
        BeanUtils.copyProperties(dto, this);
        this.from = DateUtils.getMills(dto.getFrom());
        this.to = DateUtils.getMills(dto.getTo());
        this.state = BookingState.NEW;
        this.paid = false;
        this.deposits = 0F;
        // lay thong tin ban dat
        if(Objects.nonNull(dto.getBookedTables()))
            this.bookedTables = dto.getBookedTables().stream()
              .map(e->{
                  BookedTable bt = new BookedTable(e, this.from, this.to);
                  bt.setBooking(this);//map to gen booking id db
                  return bt;
              })
              .collect(Collectors.toList());
    }

    public void copyProperties(BookingResponseDTO dto){
        BeanUtils.copyProperties(dto, this);
        if(Objects.isNull(dto.getBookedTables()) || dto.getBookedTables().isEmpty()){
            this.state = BookingState.CANCEL;
        }
        this.updateAt = DateUtils.getCurrentMils();
        //cap nhat lai thong tin ban da dat
    }
}
