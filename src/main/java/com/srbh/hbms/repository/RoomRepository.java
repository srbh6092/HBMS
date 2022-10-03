package com.srbh.hbms.repository;

import com.srbh.hbms.model.entity.Hotel;
import com.srbh.hbms.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    List<Room> findByHotel(Hotel Hotel);
}
