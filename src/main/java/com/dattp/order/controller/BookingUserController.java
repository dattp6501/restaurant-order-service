package com.dattp.order.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.dattp.order.anotation.docapi.AddAuthorizedDocAPI;
import com.dattp.order.entity.state.BookingState;
import com.dattp.order.utils.DateUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dattp.order.dto.booking.BookingCreateDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/order/user/booking")
public class BookingUserController extends Controller{

    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @RolesAllowed({"ROLE_ORDER_NEW"})
    public ResponseEntity<ResponseDTO> create(
      @RequestBody @Valid BookingCreateDTO bookingR, @RequestHeader(value = "timezone", required = false) Long timezone
    ){
        //check input
        //neu thoi gian bat dau > thoi gian ket thuc
        if(bookingR.getFrom().isAfter(bookingR.getTo()))
            throw new BadRequestException("Thời gian bắt đầu phải nhỏ hơn thòi gian kết thúc");
        //neu thoi gian bat dau < thoi gian hien tai(theo mui gio-tinh theo ngon ngu truyen vao)
        if(bookingR.getFrom().isBefore(DateUtils.getcurrentLocalDateTime().plusHours(timezone)))
            throw new BadRequestException("Thời gian bắt đầu phải lớn hơn thòi gian hiện tại");
        // get data
        Booking booking = new Booking(bookingR);
        booking.setCustomerId(jwtService.getUserId());
        //process
        return ResponseEntity.ok(
          new ResponseDTO(
            HttpStatus.OK.value(),
            "Thành công",
            bookingService.save(booking)
          )
        );
    }
    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @RolesAllowed({"ROLE_ORDER_ACCESS"})
    public ResponseEntity<?> getByCustemerId(
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "state", required = false) BookingState state,
        @RequestParam(value = "from", required = false) @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") LocalDateTime from,
        @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") LocalDateTime to,
        @RequestParam(value = "paid", required = false) Boolean paid,
        Pageable pageable
    ){
        return ResponseEntity.ok(
          new ResponseDTO(
            HttpStatus.OK.value(),
            "Thành công",
            bookingService.findAllFromDBAndCache(id, state, from, to, paid, pageable)
          )
        );
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @RolesAllowed({"ROLE_ORDER_ACCESS"})
    public ResponseEntity<ResponseDTO> getBookingDetail(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                bookingService.getBookingDetailFromDBAndCache(id)
            )
        );
    }
}