package com.srbh.hbms.service.room;

import com.srbh.hbms.model.entity.Hotel;
import com.srbh.hbms.model.entity.Room;
import com.srbh.hbms.repository.HotelRepository;
import com.srbh.hbms.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Room> getAllRooms() {

        //Returning list of all rows of Room table
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(int id) throws Exception {

        //Fetching room with id
        Optional<Room> result = roomRepository.findById(id);

        //If Room fetched with id is not present
        //Throw error
        if(!result.isPresent())
            throw new Exception("No room found with id: "+id);

        //Else return the Room fetched after the call
        return result.get();
    }

    @Override
    public Room addRoom(Room room) {

        Hotel hotel = room.getHotel();
        double noOfRoomsInHotel = hotelRepository.findAll().stream().filter(e -> e.getHotelName().equals(hotel.getHotelName())).collect(Collectors.toList()).size();
        hotel.setAvgRatePerDay((hotel.getAvgRatePerDay()*noOfRoomsInHotel+room.getRatePerDay())/(noOfRoomsInHotel+1));
        room.setHotel(hotelRepository.save(hotel));
        //Adding a row to Room table
        return roomRepository.save(room);
    }

    @Override
    public Room removeRoom(int id) throws Exception {

        //Fetching the room with id from above created method
        Room room = removeRoom(id);

        //Deleting the room fetched
        roomRepository.delete(room);

        //Returning the room fetched
        return room;
    }

}