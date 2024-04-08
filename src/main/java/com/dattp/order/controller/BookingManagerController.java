package com.dattp.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/order/manage/booking")
public class BookingManagerController extends Controller{

//    @GetMapping("")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
//    public ResponseEntity<ResponseDTO> getByCustemerId(@RequestParam("from") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date from, @RequestParam("to") @DateTimeFormat(pattern="HH:mm:ss dd/MM/yyyy") Date to, Pageable pageable){
//        List<BookingResponseDTO> list = new ArrayList<>();
//        bookingService.getAllByFromAndTo(from, to, pageable).forEach((bk)->{
//            BookingResponseDTO BkResp = new BookingResponseDTO();
//            BeanUtils.copyProperties(bk, BkResp);
//            // table
//            BkResp.setBookedTables(new ArrayList<>());
//            bk.getBookedTables().forEach((t)->{
//                BookedTableResponseDTO BTResp = new BookedTableResponseDTO();
//                BeanUtils.copyProperties(t, BTResp);
//                BkResp.getBookedTables().add(BTResp);
//            });
//            list.add(BkResp);
//        });
//        return ResponseEntity.ok().body(
//            new ResponseDTO(
//                HttpStatus.OK.value(),
//                "Thành công",
//                list
//            )
//        );
//    }

//    @GetMapping("/{booking_id}")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
//    public ResponseEntity<ResponseDTO> getBookingDetail(@PathVariable("booking_id") Long id){
//        BookingResponseDTO bkResp = new BookingResponseDTO();
//        Booking booking = bookingService.getByID(id);
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
//
//    @PostMapping("/cancel_booking")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
//    public ResponseEntity<ResponseDTO> cancelBooking(@RequestBody HashMap<String,String> req) throws Exception{
//        Long id = Long.parseLong(req.get("id"));
//        bookingService.cancelBooking(id);
//        return ResponseEntity.ok().body(
//            new ResponseDTO(
//                HttpStatus.OK.value(),
//                "Thành công",
//                null
//            )
//        );
//    }
//
//    @PostMapping("/confirm_booking")
//    @AddAuthorizedDocAPI
//    @RolesAllowed({"ROLE_ORDER_UPDATE", "ROLE_ORDER_UPDATE"})
//    public ResponseEntity<ResponseDTO> confirmBooking(@RequestBody HashMap<String,String> req) throws Exception{
//        Long id = Long.parseLong(req.get("id"));
//        bookingService.confirmBooking(id);
//        return ResponseEntity.ok().body(
//            new ResponseDTO(
//                HttpStatus.OK.value(),
//                "Thành công",
//                null
//            )
//        );
//    }

}