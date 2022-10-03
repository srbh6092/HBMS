package com.srbh.hbms.service.room;

import com.srbh.hbms.model.entity.Hotel;
import com.srbh.hbms.model.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomService {

    List<Room> getAllRooms();

    Room getRoom(int id) throws Exception;

    Room addRoom(Room room);

    Room removeRoom(int id) throws Exception;

    int numberOfRoomsInHotel(Hotel hotel);

}
