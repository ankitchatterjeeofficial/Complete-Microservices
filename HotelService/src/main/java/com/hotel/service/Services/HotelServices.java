package com.hotel.service.Services;

import com.hotel.service.Dtos.HotelDto;
import com.hotel.service.Payload.PageableResponse;

import java.util.List;

public interface HotelServices {

    //create
    HotelDto createHotel(HotelDto hotel);

    //getAll
    PageableResponse<HotelDto> getAllHotel(int pageNumber, int pageSize, String sortBy, String sortDir);


    //getSingle
    HotelDto getHotel(String hotelId);

    //update
    HotelDto updateHotel(HotelDto hotelDto,String hotelId);
    //delete
    void deleteHotel(String hotelId);

    //findByName
    List<HotelDto> searchHotel(String keyword);
}
