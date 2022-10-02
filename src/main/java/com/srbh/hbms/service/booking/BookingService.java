package com.srbh.hbms.service.booking;

import com.srbh.hbms.model.entity.Booking;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingService {

    List<Booking> getAllBookings();

    Booking getBooking(int id) throws Exception;

    Booking addBooking(Booking booking);

    Booking removeBooking(int id) throws Exception;

}