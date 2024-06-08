package com.dattp.order.storage;

import com.dattp.order.entity.Booking;
import com.dattp.order.entity.BookingTransaction;
import com.dattp.order.entity.action.BookingActionType;
import com.dattp.order.entity.state.BookedDishState;
import com.dattp.order.entity.state.BookedTableState;
import com.dattp.order.entity.state.BookingState;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.pojo.booking.BookingOverview;
import com.dattp.order.pojo.user.UserOverview;
import com.dattp.order.utils.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookingStorage extends Storage{
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Booking save(Booking bk){
    return bookingRepository.save(bk);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Booking cancel(Booking booking, UserOverview userInfo){
    //
    booking.setState(BookingState.CANCEL);
    booking.setUpdateAt(DateUtils.getCurrentMils());

    booking.getBookedTables().forEach(bt->{
      bt.setState(BookedTableState.CANCEL);
      bt.setUpdateAt(DateUtils.getCurrentMils());
    });

    if(Objects.nonNull(booking.getDishs())){
      booking.getDishs().forEach(bd->{
        bd.setState(BookedDishState.CANCEL);
        bd.setUpdateAt(DateUtils.getCurrentMils());
      });
    }
    Booking newBooking = this.save(booking);
    //transaction
    BookingTransaction bkt = new BookingTransaction();
    bkt.setBookingInfo(new BookingOverview(booking));
    bkt.setActionType(BookingActionType.CANCEL);
    bkt.setUserInfo(userInfo);
    bookingTransactionStorage.save(bkt);
    return newBooking;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Booking confirm(Booking booking, UserOverview userInfo){
    //
    booking.setState(BookingState.SUCCESS);
    booking.setUpdateAt(DateUtils.getCurrentMils());

    booking.getBookedTables().forEach(bt->{
      bt.setState(BookedTableState.SUCCESS);
      bt.setUpdateAt(DateUtils.getCurrentMils());
    });

    if(Objects.nonNull(booking.getDishs())){
      booking.getDishs().forEach(bd->{
        bd.setState(BookedDishState.SUCCESS);
        bd.setUpdateAt(DateUtils.getCurrentMils());
      });
    }
    Booking newBooking = this.save(booking);
    //transaction
    BookingTransaction bkt = new BookingTransaction();
    bkt.setBookingInfo(new BookingOverview(booking));
    bkt.setActionType(BookingActionType.CONFIRM);
    bkt.setUserInfo(userInfo);
    bookingTransactionStorage.save(bkt);
    return newBooking;
  }

  public Booking findById(Long id){
    return bookingRepository.findById(id).orElseThrow(()-> new BadRequestException("Booking id = "+id+" Not found"));
  }

  public Page<Booking> findAll(Long id, BookingState state, Long from, Long to, Long CustomerId, String custemerFullname, Boolean paid, Pageable pageable){
    return bookingRepository.findAll(specificationBooking(id, state, from, to, CustomerId, custemerFullname, paid), pageable);
  }

  private Specification<Booking> specificationBooking(Long id, BookingState state, Long from, Long to, Long CustomerId, String custemerFullname, Boolean paid) {
    return (Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if(Objects.nonNull(id)) predicates.add(criteriaBuilder.equal(root.get("id"), id));
      if(Objects.nonNull(state)) predicates.add(criteriaBuilder.equal(root.get("state"), state));
      else predicates.add(criteriaBuilder.notEqual(root.get("state"), BookingState.DELETE));
      if(Objects.nonNull(from)) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("from"), from));
      if(Objects.nonNull(to)) predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("to"), to));
      if(Objects.nonNull(CustomerId)) predicates.add(criteriaBuilder.equal(root.get("CustomerId"), CustomerId));
      if(Objects.nonNull(custemerFullname)) predicates.add(criteriaBuilder.like(root.get("custemerFullname"), custemerFullname));
      if(Objects.nonNull(paid)) predicates.add(criteriaBuilder.equal(root.get("paid"), paid));
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}