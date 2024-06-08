package com.dattp.order.controller;

import com.dattp.order.anotation.docapi.AddAuthorizedDocAPI;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.entity.state.BookingState;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/order/manage/booking")
@CrossOrigin(origins = "*")
public class BookingManagerController extends Controller{

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> getByCustemerId(
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "State", required = false) BookingState state,
        @RequestParam(value = "customerName", required = false) String customerName,
        @RequestParam(value = "paid", required = false) Boolean paid,
        @RequestParam(value = "from") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") LocalDateTime from,
        @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") LocalDateTime to,
        Pageable pageable
    ){
        return ResponseEntity.ok(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                bookingService.findAllFromDB(id, state, from, to, customerName, paid, pageable)
            )
        );
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> getBookingDetail(@PathVariable("id") Long id){
        return ResponseEntity.ok(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                bookingService.getBookingDetailFromDB(id)
            )
        );
    }
    @PostMapping(value = "/{id}/cancel", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }
//
    @PostMapping("{id}/confirm")
    @AddAuthorizedDocAPI
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> confirmBooking(@PathVariable Long id){
        bookingService.confirmBooking(id);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }

}