package com.dattp.order.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.dto.PeriodTimeResponseDTO;
import com.dattp.order.dto.PeriodsTimeBookedTableDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.service.BookedTableService;

@RestController
@RequestMapping("/api/booking/booked_table")
public class BookedTableController {
    @Autowired
    private BookedTableService bookedTableService;

    // get periad rent of the table
    @GetMapping("/get_all_period_rent_table")
    @RolesAllowed({"ROLE_ORDER_NEW","ROLE_ADMIN"})
    public ResponseEntity<ResponseDTO> get(@RequestParam("from") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date from, @RequestParam("to") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date to, Pageable pageable){
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(), 
                "Thành công", 
                bookedTableService.getPeriadrentAllTable(from, to, pageable)
            )
        );
    }


    //get all period rent of table from date_from to date_to
    @GetMapping("/get_period_rent_table/{table_id}")
    @RolesAllowed({"ROLE_ORDER_NEW"})
    public ResponseEntity<ResponseDTO> get(@PathVariable("table_id") Long tableId, @RequestParam("from") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date from, @RequestParam("to") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date to){
        PeriodsTimeBookedTableDTO table = new PeriodsTimeBookedTableDTO();
        table.setId(tableId);
        table.setTimes(new ArrayList<>());
        if(from.compareTo(to)>0){
            Date temp = from;
            from = to;
            to = temp;
        }
        if(from.compareTo(new Date())<0) from = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from); calendar.add(Calendar.DATE, 2);
        to = calendar.getTime();
        bookedTableService.getPeriodRentTable(from, to, tableId).forEach((pair)->{
            table.getTimes().add(new PeriodTimeResponseDTO(pair.getFirst(),pair.getSecond()));
        });
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", table)
        );
    }
}