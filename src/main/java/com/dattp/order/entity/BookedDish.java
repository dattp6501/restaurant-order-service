package com.dattp.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dattp.order.entity.state.BookedDishState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "BOOKED_DISH")
@Setter
@Getter
public class BookedDish {
    @Column(name = "state")
    private BookedDishState state;
    
    @Column(name = "id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="dish_id")
    private Long dishId;

    @Column(name="name")
    private String name;

    @Column(name = "imege")
    private String image;

    @Column(name="total")
    private Integer total;

    @Column(name="price")
    private Float price;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "update_at")
    private Long updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private Booking booking;

    public BookedDish(){}

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BookedDish)) return false;
        BookedDish other = (BookedDish) obj;
        if(Objects.equals(this.id, other.id)) return Objects.equals(this.id, other.id);
        // dish was placed on the menu
        return Objects.equals(this.booking.getId(), other.booking.getId()) &&this.dishId==other.dishId;
    }

}
