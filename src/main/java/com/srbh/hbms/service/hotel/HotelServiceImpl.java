package com.srbh.hbms.service.hotel;

import com.srbh.hbms.model.entity.Hotel;
import com.srbh.hbms.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(int id) throws Exception {

        //Fetching hotel with id
        Optional<Hotel> result = hotelRepository.findById(id);

        //If Hotel fetched with id is not present
        //Throw error
        if(!result.isPresent())
            throw new Exception("No hotel found with id: "+id);

        //Else return the Hotel fetched after the call
        return result.get();
    }

    @Override
    public Hotel addHotel(Hotel hotel) {

        //Adding a row to User table
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel removeHotel(int id) throws Exception {

        //Fetching the hotel with id from above created method
        Hotel hotel = getHotel(id);

        //Deleting the hotel fetched
        hotelRepository.delete(hotel);

        //Returning the hotel fetched
        return hotel;
    }

}