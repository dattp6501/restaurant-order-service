package com.dattp.order.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOKING")
@Getter
@Setter
@AllArgsConstructor
public class Booking {
    @Column(name = "state")
    private int state;

    @Column(name="id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private long CustomerId;

    @Column(name="custemer_fullname")
    private String custemerFullname;

    @Column(name = "date", nullable = false)
    // @CreatedDate
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;

    @Column(name = "from_", nullable = false)
    // @CreatedDate
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date from;

    @Column(name = "to_", nullable = false)
    // @CreatedDate
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date to;

    @Column(name = "deposits")
    private float deposits;

    @Column(name = "paid", nullable = false)
    private boolean paid;

    @Column(name = "desciption")
    private String description;

    @OneToMany(mappedBy = "booking", cascade ={CascadeType.ALL}, fetch=FetchType.LAZY )
    private List<BookedTable> bookedTables;

    @OneToMany(mappedBy="booking", cascade={CascadeType.ALL})
    private List<BookedDish> dishs;

    public Booking(){
        super();
    }
}
