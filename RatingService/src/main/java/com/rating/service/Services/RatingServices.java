package com.rating.service.Services;

import com.rating.service.Dtos.RatingDto;
import com.rating.service.Payload.PageableResponse;

import java.util.List;

public interface RatingServices {


    //create Rating
    RatingDto createRating(RatingDto ratingDto);

    // get Rating
    PageableResponse<RatingDto> getAllRating(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get Rating by UserId
    List<RatingDto> getRatingByUserId(String userId);

    //get Rating by HotelId
    PageableResponse<RatingDto> getRatingByHotelId(String hotelId,int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all rating id
    RatingDto getRating(String ratingId);

    //update
    RatingDto updateRating(RatingDto ratingDto,String ratingId);

    //delete
    void deleteRating(String ratingId);










}
