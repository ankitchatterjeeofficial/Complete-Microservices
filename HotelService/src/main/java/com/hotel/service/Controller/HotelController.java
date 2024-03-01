package com.hotel.service.Controller;

import com.hotel.service.Dtos.HotelDto;
import com.hotel.service.Payload.ApiResponseMessage;
import com.hotel.service.Payload.PageableResponse;
import com.hotel.service.Services.HotelServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelServices hotelServices;

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@Valid @RequestBody HotelDto hotelDto)
    {
        HotelDto hotelDto1 = hotelServices.createHotel(hotelDto);
        return new ResponseEntity<>(hotelDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{hotelId}")
    public  ResponseEntity<HotelDto> updateHotel(@PathVariable String hotelId,@Valid @RequestBody HotelDto hotelDto)
    {
        HotelDto updateHotel = hotelServices.updateHotel(hotelDto, hotelId);
        return new ResponseEntity<>(updateHotel,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<HotelDto>> getAllHotel(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "aesc",required = false) String sortDir
    )
    {
        return new ResponseEntity<>(hotelServices.getAllHotel(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<ApiResponseMessage> deleteHotel(@PathVariable String hotelId)
    {
        hotelServices.deleteHotel(hotelId);
        ApiResponseMessage message1 = ApiResponseMessage.builder()
                .message("Hotel is Successfully Deleted!!!!")
                .success(true).status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message1,HttpStatus.OK);
    }

    @GetMapping("/{hotelId}")
    public  ResponseEntity<HotelDto> getSingleHotel(@PathVariable String hotelId)
    {
        return new ResponseEntity<>(hotelServices.getHotel(hotelId),HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<HotelDto>> searchHotel(@PathVariable String keywords)
    {
        return new ResponseEntity<>(hotelServices.searchHotel(keywords),HttpStatus.OK);
    }

}
