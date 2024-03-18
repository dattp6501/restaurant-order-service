package com.dattp.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.BookedDishResponseDTO;
import com.dattp.order.dto.BookedTableResponseDTO;
import com.dattp.order.dto.BookingResponseDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.entity.Booking;
import com.dattp.order.service.BookingService;


@RestController
@RequestMapping("/api/order/manage/booking")
public class BookingManagerController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/get_booking")
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> getByCustemerId(@RequestParam("from") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date from, @RequestParam("to") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date to, Pageable pageable){
        List<BookingResponseDTO> list = new ArrayList<>();
        bookingService.getAllByFromAndTo(from, to, pageable).stream().forEach((bk)->{
            BookingResponseDTO BkResp = new BookingResponseDTO();
            BeanUtils.copyProperties(bk, BkResp);
            // table
            BkResp.setBookedTables(new ArrayList<>());
            bk.getBookedTables().stream().forEach((t)->{
                BookedTableResponseDTO BTResp = new BookedTableResponseDTO();
                BeanUtils.copyProperties(t, BTResp);
                BkResp.getBookedTables().add(BTResp);
            });
            list.add(BkResp);
        });
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                list
            )
        );
    }

    @GetMapping("/get_booking_detail/{booking_id}")
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> getBookingDetail(@PathVariable("booking_id") Long id){
        BookingResponseDTO bkResp = new BookingResponseDTO();
        Booking booking = bookingService.getByID(id);
        BeanUtils.copyProperties(booking, bkResp);
        // table
        bkResp.setBookedTables(new ArrayList<>());
        booking.getBookedTables().stream().forEach((t)->{
            BookedTableResponseDTO BTR = new BookedTableResponseDTO();
            BeanUtils.copyProperties(t, BTR);
            bkResp.getBookedTables().add(BTR);
        });
        // dish
        bkResp.setDishs(new ArrayList<>());
        if(!booking.getDishs().isEmpty()){
            booking.getDishs().stream().forEach((d)->{
                BookedDishResponseDTO BDR = new BookedDishResponseDTO();
                BeanUtils.copyProperties(d, BDR);
                bkResp.getDishs().add(BDR);
            });
        }
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công",
                bkResp
            )
        );
    }

    @PostMapping
    @RequestMapping("/cancel_booking")
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> cancelBooking(@RequestBody HashMap<String,String> req) throws Exception{
        Long id = Long.parseLong(req.get("id"));
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công",
                null
            )
        );
    }

    @PostMapping
    @RequestMapping("/confirm_booking")
    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
    public ResponseEntity<ResponseDTO> confirmBooking(@RequestBody HashMap<String,String> req) throws Exception{
        Long id = Long.parseLong(req.get("id"));
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