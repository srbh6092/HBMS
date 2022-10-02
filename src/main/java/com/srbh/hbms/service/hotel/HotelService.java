package com.srbh.hbms.service.hotel;

import com.srbh.hbms.model.entity.Hotel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelService {

    List<Hotel> getAllHotels();

    Hotel getHotel(int id) throws Exception;

    Hotel addHotel(Hotel hotel);

    Hotel removeHotel(int id) throws Exception;

}