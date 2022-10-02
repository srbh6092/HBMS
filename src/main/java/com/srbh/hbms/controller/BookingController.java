package com.srbh.hbms.controller;

import com.srbh.hbms.model.entity.*;
import com.srbh.hbms.model.request.NewBookingRequest;
import com.srbh.hbms.service.booking.BookingService;
import com.srbh.hbms.service.hotel.HotelService;
import com.srbh.hbms.service.payment.PaymentService;
import com.srbh.hbms.service.room.RoomService;
import com.srbh.hbms.service.transaction.TransactionService;
import com.srbh.hbms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    RoomService roomService;

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable("id") int id) throws Exception {
        return bookingService.getBooking(id);
    }

    @PostMapping("/{userId}")
    public Booking addBooking(@PathVariable("userId") int userId, @RequestBody NewBookingRequest newBookingRequest) throws Exception {

        User user = userService.getUser(userId);

        Date from = newBookingRequest.getBookedFrom();
        Date to = newBookingRequest.getBookedTo();
        long diff = to.getTime()- from.getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        List<Room> rooms = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();
        Set<Integer> hotelId = new HashSet<>();

        for( int roomId: newBookingRequest.getRoomIDs()){
            Room room = roomService.getRoom(roomId);
            hotelId.add(room.getHotel().getHotelId());
            if(hotelId.size()>1)
                throw new Exception("All rooms must be from same hotel");
            if(!room.getIsAvailable())
                throw new Exception("Room id: "+ roomId + " is not available");
            room.setIsAvailable(false);
            roomService.addRoom(room);
            rooms.add(room);
        }

        Hotel hotel = rooms.get(0).getHotel();

        for( Room room: rooms){
            double amount = room.getRatePerDay()*days;
            Transaction transaction = Transaction.builder()
                    .amount(amount)
                    .build();
            Payment payment= Payment.builder()
                    .transaction(transactionService.addTransaction(transaction))
                    .build();
            payments.add(paymentService.addPayment(payment));
        }

        Booking booking = Booking.builder()
                .bookedFrom(from)
                .bookedTo(to)
                .hotel(hotel)
                .user(user)
                .noOfAdults(newBookingRequest.getNoOfAdults())
                .noOfChildren(newBookingRequest.getNoOfChildren())
                .rooms(rooms)
                .payment(payments)
                .build();

        return bookingService.addBooking(booking);
    }
    @DeleteMapping("/{id}")
    public Booking removeBooking(@PathVariable("id") int id) throws Exception {
        return bookingService.removeBooking(id);
    }

}