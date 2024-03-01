package com.rating.service.Controllers;

import com.rating.service.Dtos.RatingDto;
import com.rating.service.Payload.ApiResponseMessage;
import com.rating.service.Payload.PageableResponse;
import com.rating.service.Services.RatingServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingControllers {

    @Autowired
    private RatingServices ratingServices;

    @PostMapping
    public ResponseEntity<RatingDto> createRating(@Valid @RequestBody RatingDto ratingDto)
    {
        RatingDto rating = ratingServices.createRating(ratingDto);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }
    @PutMapping("/{ratingId}")
    public  ResponseEntity<RatingDto> updateRating(@PathVariable String ratingId,@Valid @RequestBody RatingDto ratingDto)
    {
        RatingDto rating = ratingServices.updateRating(ratingDto, ratingId);
        return new ResponseEntity<>(rating,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<RatingDto>> getAllRating(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "userId",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "aesc",required = false) String sortDir
    )
    {
        return new ResponseEntity<>(ratingServices.getAllRating(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingDto>> getAllRatingUser(@PathVariable String userId)
    {
        return new ResponseEntity<>(ratingServices.getRatingByUserId(userId),HttpStatus.OK);
    }
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<PageableResponse<RatingDto>> getAllRatingHotel(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "userId",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "aesc",required = false) String sortDir,
            @PathVariable String hotelId
    )
    {
        return new ResponseEntity<>(ratingServices.getRatingByHotelId(hotelId,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<ApiResponseMessage> deleteHotel(@PathVariable String ratingId)
    {
        ratingServices.deleteRating(ratingId);
        ApiResponseMessage message1 = ApiResponseMessage.builder()
                .message("Rating is Successfully Deleted!!!!")
                .success(true).status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message1,HttpStatus.OK);
    }

    @GetMapping("/{ratingId}")
    public  ResponseEntity<RatingDto> getSingleRating(@PathVariable String ratingId)
    {
        return new ResponseEntity<>(ratingServices.getRating(ratingId),HttpStatus.OK);

    }
}
