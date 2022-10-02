package com.srbh.hbms.service.booking;

import com.srbh.hbms.model.entity.Booking;
import com.srbh.hbms.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public List<Booking> getAllBookings() {

        //Returning list of all rows of Booking table
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBooking(int id) throws Exception {

        //Fetching booking with id
        Optional<Booking> result = bookingRepository.findById(id);

        //If Booking fetched with id is not present
        //Throw error
        if(!result.isPresent())
            throw new Exception("No booking found with id: "+id);

        //Else return the Booking fetched after the call
        return result.get();
    }

    @Override
    public Booking addBooking(Booking booking) {

        //Adding a row to Booking table
        return bookingRepository.save(booking);
    }

    @Override
    public Booking removeBooking(int id) throws Exception {

        //Fetching the booking with id from above created method
        Booking booking = getBooking(id);

        //Deleting the booking fetched
        bookingRepository.delete(booking);

        //Returning the booking fetched
        return booking;
    }

}