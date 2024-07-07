package com.dattp.order.entity;

import com.dattp.order.converter.BookingOverviewConverter;
import com.dattp.order.converter.UserOverviewConverter;
import com.dattp.order.entity.action.BookingActionType;
import com.dattp.order.pojo.booking.BookingOverview;
import com.dattp.order.pojo.user.UserOverview;
import com.dattp.order.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOOKING_TRANSACTION")
@Getter
@Setter
public class BookingTransaction {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    private BookingActionType actionType;

    @Column(name = "employee_info", columnDefinition = "TEXT")
    @Convert(converter = UserOverviewConverter.class)
    private UserOverview userInfo;

    @Column(name = "booking_info", columnDefinition = "TEXT")
    @Convert(converter = BookingOverviewConverter.class)
    private BookingOverview bookingInfo;

    @Column(name = "create_at")
    private Long createAt = DateUtils.getCurrentMils();

    @PrePersist
    protected void onCreate() {
        this.createAt = DateUtils.getCurrentMils();
    }

    public BookingTransaction() {
        super();
    }
}