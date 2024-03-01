package com.hotel.service.Repository;

import com.hotel.service.Dtos.HotelDto;
import com.hotel.service.Entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,String> {

    List<Hotel> findByNameContaining(String string);


}
