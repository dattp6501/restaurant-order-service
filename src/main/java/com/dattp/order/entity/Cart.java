package com.dattp.order.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CART")
@Getter
@Setter
public class Cart {
    @Column(name="id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<TableInCart> tables;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<DishInCart> dishs;

    public Cart() {
        super();
    }

    public Cart(Long userId) {
        this.userId = userId;
    }
}