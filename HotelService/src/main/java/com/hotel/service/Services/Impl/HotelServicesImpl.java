package com.hotel.service.Services.Impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.service.Dtos.HotelDto;
import com.hotel.service.Entities.Hotel;
import com.hotel.service.Exception.ResourceNotFoundException;
import com.hotel.service.Helper.PageHelper;
import com.hotel.service.Payload.PageableResponse;
import com.hotel.service.Repository.HotelRepository;
import com.hotel.service.Services.HotelServices;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServicesImpl implements HotelServices {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger= LoggerFactory.getLogger(HotelServicesImpl.class);

    @Override
    public HotelDto createHotel(HotelDto hoteldto) {
        String hotelId= UUID.randomUUID().toString();
        hoteldto.setHotelId(hotelId);
        logger.info("code is Running for Save User");
        Hotel hotel = mapper.map(hoteldto, Hotel.class);
        Hotel save = hotelRepository.save(hotel);
        return mapper.map(save,HotelDto.class);
    }

    @Override
    public PageableResponse<HotelDto> getAllHotel(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);


        Page<Hotel> page=hotelRepository.findAll(pageable);
        PageableResponse<HotelDto> response = PageHelper.getPageableResponse(page, HotelDto.class);
        return response;

    }

    @Override
    public HotelDto getHotel(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("HotelId not Found for Given Hotel "));
        HotelDto hotelDto = mapper.map(hotel, HotelDto.class);
        return hotelDto;

    }

    @Override
    public HotelDto updateHotel(HotelDto hotelDto, String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with given id"));
        hotel.setName(hotelDto.getName());
        hotel.setAbout(hotelDto.getAbout());
        hotel.setLocation(hotelDto.getLocation());
        //user.setUserId(userDto.getUserId());

        Hotel saveHotel = hotelRepository.save(hotel);
        HotelDto updatedDto = mapper.map(saveHotel, HotelDto.class);
        return updatedDto;

    }

    @Override
    public void deleteHotel(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with given id "));
        hotelRepository.delete(hotel);
    }

    @Override
    public List<HotelDto> searchHotel(String keyword) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Hotel> hotels = hotelRepository.findByNameContaining(keyword);
        List<HotelDto> collect = hotels.stream().map(o -> objectMapper.convertValue(o, HotelDto.class)).collect(Collectors.toList());
        return collect;
    }
}
