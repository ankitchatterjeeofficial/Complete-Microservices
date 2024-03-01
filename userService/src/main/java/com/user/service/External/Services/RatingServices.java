package com.user.service.External.Services;

import com.user.service.Entities.Rating;
import com.user.service.PayLoad.ApiResponseMessage;
import com.user.service.PayLoad.PageableResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingServices {

    //create  post

    @PostMapping("/ratings")
     Rating createRating(Rating values);

    //update put
    @PutMapping("/ratings/{ratingId}")
    ResponseEntity<Rating> updateRating(@PathVariable String ratingId, Rating values);

    @DeleteMapping("/ratings/{ratingId}")
    ResponseEntity<ApiResponseMessage> deleteRating(@PathVariable String ratingId);

    @GetMapping("/ratings")
    PageableResponse<Rating> getAllRating();

    @GetMapping("/ratings/user/{userId}")
    List<Rating> getAllRatingUser(@PathVariable String userId);





}
