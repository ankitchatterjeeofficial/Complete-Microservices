package com.user.service.RatingServicesTest;

import com.user.service.Dtos.UserDto;
import com.user.service.Entities.Rating;
import com.user.service.External.Services.RatingServices;
import com.user.service.PayLoad.ApiResponseMessage;
import com.user.service.PayLoad.PageableResponse;
import com.user.service.Services.UserServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
public class RatingTest {
    @Autowired
    private RatingServices ratingServices;

    private UserServices userServices;

    @Test
    void createRating()
    {
        Rating rating = Rating.builder().
                rating(4)
                .userId("1234")
                .hotelId("4567")
                .remark("This is good")
                .build();

        Rating rating1 = ratingServices.createRating(rating);
        System.out.println("New Rating Created");


    }
    @Test
    void updateRating()
    {
        Rating rating = Rating.builder().
                rating(4)
                .userId("1234")
                .hotelId("4567")
                .remark("This is very very very good")
                .build();
        ResponseEntity<Rating> ratingResponseEntity = ratingServices.updateRating("f0ee6900-6587-4c1f-98d1-2d7b5d02ebf9", rating);
        Rating body = ratingResponseEntity.getBody();
        System.out.println(" "+body);

    }

    @Test
    void deleteRating()
    {
        ResponseEntity<ApiResponseMessage> api = ratingServices.deleteRating("f0ee6900-6587-4c1f-98d1-2d7b5d02ebf9");
        ApiResponseMessage body = api.getBody();
        String message = body.getMessage();
        System.out.println(message);
    }

    @Test
    void GetAllRating()
    {
        PageableResponse<Rating> allRating = ratingServices.getAllRating();
        List<Rating> content = allRating.getContent();
        content.forEach(System.out::println);
    }

    @Test
    void getAllRatingUser()
    {
        List<Rating> allRatingUser = ratingServices.getAllRatingUser("1081ee00-30d5-470e-8cb1-ce413d03d049");
        allRatingUser.forEach(System.out::println);
        //allRatingUser.strea
        //System.out.println(body);
    }


}
