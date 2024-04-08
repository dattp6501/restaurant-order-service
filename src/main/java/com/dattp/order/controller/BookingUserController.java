package com.dattp.order.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.dattp.order.anotation.docapi.AddAuthorizedDocAPI;
import com.dattp.order.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dattp.order.dto.booking.BookingCreateDTO;
import com.dattp.order.dto.ResponseDTO;
import com.dattp.order.entity.Booking;
import com.dattp.order.exception.BadRequestException;


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

//    @PostMapping(value = "/dish", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_NEW"})
//    public ResponseEntity<ResponseDTO> addDish(@RequestBody @Valid BookingDishRequestDTO bookingDishRequestDTO){
////        List<BookedDish> dishs = new ArrayList<>();
////        bookingDishRequestDTO.getDishs().forEach((dResp)->{
////            BookedDish dish = new BookedDish();
////            BeanUtils.copyProperties(dResp, dish);
////            dish.setState(ApplicationConfig.DEFAULT_STATE);
////            dishs.add(dish);
////        });
////        bookingService.addDishToBooking(bookingDishRequestDTO.getId(), dishs);
//        return ResponseEntity.ok(
//          new ResponseDTO(
//            HttpStatus.OK.value(),
//            "Thành công",
//            null
//          )
//        );
//    }
    
    // get all booking of user
//    @GetMapping("")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_ACCESS"})
//    public ResponseEntity<?> getByCustemerId(Pageable pageable){
//        List<BookingResponseDTO> list = bookingService.getByCustemerId(2, pageable).stream()
//          .map(BookingResponseDTO::new)
//          .collect(Collectors.toList());
//
//        return ResponseEntity.ok(
//          new ResponseDTO(
//            HttpStatus.OK.value(),
//            "Thành công",
//            list
//          )
//        );
//    }

//    @GetMapping("/{booking_id}")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_ACCESS"})
//    public ResponseEntity<ResponseDTO> getBookingDetail(@PathVariable("booking_id") Long id) throws Exception{
//        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
//        BookingResponseDTO bkResp = new BookingResponseDTO();
//        Booking booking = bookingService.getByID(id);
//        if(userId != booking.getCustomerId()) throw new Exception("Bạn không có lịch đặt này");
//        BeanUtils.copyProperties(booking, bkResp);
//        // table
//        bkResp.setBookedTables(new ArrayList<>());
//        booking.getBookedTables().forEach((t)->{
//            BookedTableResponseDTO BTR = new BookedTableResponseDTO();
//            BeanUtils.copyProperties(t, BTR);
//            bkResp.getBookedTables().add(BTR);
//        });
//        // dish
//        bkResp.setDishs(new ArrayList<>());
//        if(!booking.getDishs().isEmpty()){
//            booking.getDishs().forEach((d)->{
//                BookedDishResponseDTO BDR = new BookedDishResponseDTO();
//                BeanUtils.copyProperties(d, BDR);
//                bkResp.getDishs().add(BDR);
//            });
//        }
//        return ResponseEntity.ok().body(
//            new ResponseDTO(
//                HttpStatus.OK.value(),
//                "Thành công",
//                bkResp
//            )
//        );
//    }
}