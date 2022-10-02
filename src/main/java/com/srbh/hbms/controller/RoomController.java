package com.srbh.hbms.controller;

import com.srbh.hbms.model.entity.Hotel;
import com.srbh.hbms.model.entity.Photo;
import com.srbh.hbms.model.entity.Room;
import com.srbh.hbms.model.request.AddRoomRequest;
import com.srbh.hbms.service.hotel.HotelService;
import com.srbh.hbms.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    HotelService hotelService;

    @GetMapping
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable("id") int id) throws Exception {
        return roomService.getRoom(id);
    }

    @PostMapping("/{hotelId}")
    public Room addRoom(@PathVariable("hotelId") int hotelId, @RequestBody AddRoomRequest addRoomRequest) throws Exception {
        Hotel hotel = hotelService.getHotel(hotelId);
        List<String> photoURLs = addRoomRequest.getPhotos();
        List<Photo> photos = new ArrayList<>();
        for(String url: photoURLs){
            photos.add(Photo.builder()
                    .photoURL(url)
                    .build());
        }
        Room room = Room.builder()
                .roomNo(addRoomRequest.getRoomNo())
                .roomType(addRoomRequest.getRoomType())
                .hotel(hotel)
                .isAvailable(true)
                .ratePerDay(addRoomRequest.getRatePerDay())
                .photos(photos)
                .build();
        return roomService.addRoom(room);
    }
    @DeleteMapping("/{id}")
    public Room removeRoom(@PathVariable("id") int id) throws Exception {
        return roomService.removeRoom(id);
    }

}