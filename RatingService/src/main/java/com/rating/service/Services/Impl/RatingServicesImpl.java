package com.rating.service.Services.Impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rating.service.Dtos.RatingDto;
import com.rating.service.Entities.Rating;
import com.rating.service.Exception.ResourceNotFoundException;
import com.rating.service.Helper.PageHelper;
import com.rating.service.Payload.PageableResponse;
import com.rating.service.Repository.RatingRepository;
import com.rating.service.Services.RatingServices;
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
public class RatingServicesImpl implements RatingServices {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger= LoggerFactory.getLogger(RatingServicesImpl.class);

    @Override
    public RatingDto createRating(RatingDto ratingDto) {
        String ratingId= UUID.randomUUID().toString();
        ratingDto.setRatingId(ratingId);
        logger.info("code is Running for Save Rating");
        Rating rating = mapper.map(ratingDto, Rating.class);
        Rating save = ratingRepository.save(rating);
        return mapper.map(save,RatingDto.class);
    }

    @Override
    public PageableResponse<RatingDto> getAllRating(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);


        Page<Rating> page=ratingRepository.findAll(pageable);
        PageableResponse<RatingDto> response = PageHelper.getPageableResponse(page, RatingDto.class);
        return response;
    }

    @Override
    public List<RatingDto> getRatingByUserId(String userId) {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Rating> ratings=ratingRepository.findByUserId(userId);
        List<RatingDto> collect = ratings.stream().map(o -> objectMapper.convertValue(o, RatingDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PageableResponse<RatingDto> getRatingByHotelId(String hotelId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);


        Page<Rating> page=ratingRepository.findByHotelId(hotelId,pageable);
        PageableResponse<RatingDto> response = PageHelper.getPageableResponse(page, RatingDto.class);
        return response;
    }

    @Override
    public RatingDto getRating(String ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("RatingId not Found in Database "));
        RatingDto ratingDto = mapper.map(rating, RatingDto.class);
        return ratingDto;
    }

    @Override
    public RatingDto updateRating(RatingDto ratingDto, String ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("RatingId not Found in Database for updating"));
        rating.setRemark(ratingDto.getRemark());
        rating.setRating(ratingDto.getRating());

        Rating save = ratingRepository.save(rating);
        RatingDto map = mapper.map(save, RatingDto.class);
        return map;
    }

    @Override
    public void deleteRating(String ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("RatingId not found with given id for Deletion"));
        ratingRepository.delete(rating);

    }
}
