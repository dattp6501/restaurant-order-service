package com.dattp.order.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.security.RolesAllowed;

import com.dattp.order.anotation.docapi.AddAuthorizedDocAPI;
import com.dattp.order.utils.DateUtils;
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

@RestController
@RequestMapping("/api/booking/booked_table")
public class BookedTableController extends Controller{

    // get periad rent of the table
//    @GetMapping("/get_all_period_rent_table")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_NEW","ROLE_ADMIN"})
//    public ResponseEntity<ResponseDTO> get(@RequestParam("from") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date from, @RequestParam("to") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date to, Pageable pageable){
//        return ResponseEntity.ok().body(
//            new ResponseDTO(
//                HttpStatus.OK.value(),
//                "Thành công",
//                bookedTableService.getPeriadrentAllTable(from, to, pageable)
//            )
//        );
//    }


    //get all period rent of table from date_from to date_to
//    @GetMapping("/get_period_rent_table/{table_id}")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_NEW"})
//    public ResponseEntity<ResponseDTO> get(@PathVariable("table_id") Long tableId, @RequestParam("from") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") LocalDateTime fromReq, @RequestParam("to") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") LocalDateTime toReq){
//        PeriodsTimeBookedTableDTO table = new PeriodsTimeBookedTableDTO();
//        table.setId(tableId);
//        table.setTimes(new ArrayList<>());
//        Long from = DateUtils.getMills(fromReq);
//        Long to = DateUtils.getMills(toReq);
//        if(from.compareTo(to)>0){
//            Long temp = from;
//            from = to;
//            to = temp;
//        }
//        if(from.compareTo(DateUtils.getCurrentMils())<0) from = DateUtils.getCurrentMils();
////        Calendar calendar = Calendar.getInstance();
////        calendar.setTime(from); calendar.add(Calendar.DATE, 2);
////        to = calendar.getTime();
//        bookedTableService.getPeriodRentTable(from, to, tableId).forEach((pair)->{
//            table.getTimes().add(new PeriodTimeResponseDTO(pair.getFirst(),pair.getSecond()));
//        });
//        return ResponseEntity.ok().body(
//            new ResponseDTO(HttpStatus.OK.value(), "Thành công", table)
//        );
//    }
}