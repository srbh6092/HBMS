package com.srbh.hbms.controller;

import com.srbh.hbms.model.entity.Hotel;
import com.srbh.hbms.model.request.AddHotelRequest;
import com.srbh.hbms.service.hotel.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public Hotel getUser(@PathVariable("id") int id) throws Exception {
        return hotelService.getHotel(id);
    }

    @PostMapping
    public Hotel addHotel(@RequestBody AddHotelRequest addHotelRequest){
        Hotel hotel = Hotel.builder()
                .hotelName(addHotelRequest.getHotelName())
                .city(addHotelRequest.getCity())
                .address(addHotelRequest.getAddress())
                .description(addHotelRequest.getDescription())
                .email(addHotelRequest.getEmail())
                .phone(addHotelRequest.getPhone())
                .website(addHotelRequest.getWebsite())
                .avgRatePerDay(0.0)
                .build();
        return hotelService.addHotel(hotel);
    }

    @DeleteMapping("/{id}")
    public Hotel removeHotel(@PathVariable("id") int id) throws Exception {
        return hotelService.removeHotel(id);
    }

}