package com.rating.service.Repository;

import com.rating.service.Entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.LinkedList;
import java.util.List;

public interface RatingRepository extends MongoRepository<Rating,String> {

    List<Rating> findByUserId(String userId);

    Page<Rating> findByHotelId(String hotelId,Pageable pageable);
}
