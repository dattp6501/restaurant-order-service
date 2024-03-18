package com.dattp.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.order.config.ApplicationConfig;
import com.dattp.order.dto.BookedDishResponseDTO;
import com.dattp.order.dto.BookedTableResponseDTO;
import com.dattp.order.dto.BookingDishRequestDTO;
import com.dattp.order.dto.BookingRequestDTO;
import com.dattp.order.dto.BookingResponseDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.entity.BookedDish;
import com.dattp.order.entity.BookedTable;
import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;
import com.dattp.order.service.BookingService;


@RestController
@RequestMapping("/api/order/user/booking")
public class BookingUserController {
//    @Autowired
//    private KafkaTemplate<String,BookingResponseDTO> kafkaTemplateBooking;

    @Autowired
    private BookingService bookingService;


    @PostMapping(value="/save")
    @RolesAllowed({"ROLE_ORDER_NEW"})
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid BookingRequestDTO bookingR) throws BadRequestException {
        //check input 
        if(bookingR.getFrom().compareTo(bookingR.getTo())>=0)
            throw new BadRequestException("Thời gian bắt đầu phải nhỏ hơn thòi gian kết thúc");
        // get data
        Booking booking = new Booking();
        // lay thong tin phieu dat ban
        BeanUtils.copyProperties(bookingR, booking);
        booking.setState(ApplicationConfig.DEFAULT_STATE);
        booking.setPaid(false);
        booking.setCustomerId(
            Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        booking.setDate(new Date());
        booking.setDeposits(0);
        // lay thong tin ban dat
        List<BookedTable> tables = new ArrayList<>();//danh sach ban thoa man
        bookingR.getBookedTables().stream().forEach((bt)->{
            BookedTable table = new BookedTable();
            BeanUtils.copyProperties(bt, table);
            table.setFrom(booking.getFrom());
            table.setTo(booking.getTo());
            table.setState(ApplicationConfig.DEFAULT_STATE);
            table.setBooking(booking);
            tables.add(table);
        });
        booking.setBookedTables(tables);
        Booking newBooking = bookingService.save(booking);
        // response
        // phieu dat ban
        BookingResponseDTO responseBookingDTO = new BookingResponseDTO();
        BeanUtils.copyProperties(newBooking, responseBookingDTO);
        // ban
        responseBookingDTO.setBookedTables(new ArrayList<>());
        for(BookedTable newTable : booking.getBookedTables()){
            BookedTableResponseDTO tableDTO = new BookedTableResponseDTO();
            BeanUtils.copyProperties(newTable, tableDTO);
            responseBookingDTO.getBookedTables().add(tableDTO);
        }
//        kafkaTemplateBooking.send("newOrder",responseBookingDTO);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                responseBookingDTO
            )
        );
    }

    @PostMapping(value = "/add_dish")
    @RolesAllowed({"ROLE_ORDER_NEW"})
    public ResponseEntity<ResponseDTO> addDish(@RequestBody @Valid BookingDishRequestDTO bookingDishRequestDTO) throws Exception{
        List<BookedDish> dishs = new ArrayList<>();
        bookingDishRequestDTO.getDishs().stream().forEach((dResp)->{
            BookedDish dish = new BookedDish();
            BeanUtils.copyProperties(dResp, dish);
            dish.setState(ApplicationConfig.DEFAULT_STATE);
            dishs.add(dish);
        });
        bookingService.addDishToBooking(bookingDishRequestDTO.getId(), dishs);
        return ResponseEntity.ok().body(
            new ResponseDTO(
                HttpStatus.OK.value(),
                "Thành công",
                null
            )
        );
    }
    
    // get all booking of user
    @GetMapping("/get_booking")
    @RolesAllowed({"ROLE_ORDER_ACCESS"})
    public ResponseEntity<ResponseDTO> getByCustemerId(Pageable pageable){
        List<BookingResponseDTO> list = new ArrayList<>();
        bookingService.getByCustemerId(
            Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName()), //get id user
            pageable
        ).getContent().stream().forEach((bk)->{
            BookingResponseDTO bkDTO = new BookingResponseDTO();
            BeanUtils.copyProperties(bk, bkDTO);
            // table
            bkDTO.setBookedTables(new ArrayList<>());
            bk.getBookedTables().stream().forEach((t)->{
                BookedTableResponseDTO BTR = new BookedTableResponseDTO();
                BeanUtils.copyProperties(t, BTR);
                bkDTO.getBookedTables().add(BTR);
            });
            list.add(bkDTO);
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
    @RolesAllowed({"ROLE_ORDER_ACCESS"})
    public ResponseEntity<ResponseDTO> getBookingDetail(@PathVariable("booking_id") Long id) throws Exception{
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        BookingResponseDTO bkResp = new BookingResponseDTO();
        Booking booking = bookingService.getByID(id);
        if(userId.longValue() != booking.getCustomerId()) throw new Exception("Bạn không có lịch đặt này"); 
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
}