package com.user.service.Services.Impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.service.Entities.Hotel;
import com.user.service.Entities.Rating;
import com.user.service.External.Services.HotelServices;
import com.user.service.External.Services.RatingServices;
import com.user.service.PayLoad.PageableResponse;
import com.user.service.Dtos.UserDto;
import com.user.service.Entities.User;
import com.user.service.Exception.ResourceNotFoundException;
import com.user.service.Helper.PageHelper;
import com.user.service.Repository.UserRepository;
import com.user.service.Services.UserServices;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelServices hotelServices;

    @Autowired
    private RatingServices ratingServices;

    private Logger logger= LoggerFactory.getLogger(UserServicesImpl.class);

    @Override
    public UserDto saveUser(UserDto userdto) {
        String userId= UUID.randomUUID().toString();
        userdto.setUserId(userId);
        logger.info("code is Running for Save User");
        User user = mapper.map(userdto, User.class);
        User save = userRepository.save(user);
        return mapper.map(save,UserDto.class);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);


        Page<User> page=userRepository.findAll(pageable);
        logger.info("All users {}", page.getContent());
        PageableResponse<UserDto> responses = PageHelper.getPageableResponse(page, UserDto.class);
//            for(PageableResponse<UserDto> response: responses)
//            {
//                //response.setContent();
//                ratingServices.getAllRatingUser(response.getClass().getName().ge)
//            }
        logger.info("All users final {}, PageNumber {}, pageSize {}, totalElements {} ,totalPages {}, lastPage {} ", responses.getContent(),responses.getPageNumber(),responses.getPageSize(),responses.getTotalElements(),responses.getTotalPages(),responses.isLastPage());

        return  responses;
    }

    @Override
    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserId not Found for Given User :- " + userId));

        //ObjectMapper objmapper = new ObjectMapper();
      //  objmapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        // http://localhost:8083/ratings/user/38b2337d-0fbd-4fef-8a2d-c3f20000569e  for user Pranjal
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(), Rating[].class);
        logger.info(" Rating of users :{}",ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        // user.setRating(ratings);
        //User save = userRepository.save(user);
        //logger.info("{}",user);
        List<Rating> ratingList = Arrays.stream(ratingsOfUser).map(rating ->
        {
            logger.info(" Hotel Id is  {}",rating.getHotelId().toString());
            //api call of hotel
        //url :  http://localhost:8082/hotels/4fa352b0-a984-4979-a88c-f416f88fcda3
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelServices.getHotel(rating.getHotelId());
        //  logger.info(" Response Status code is  {}",rating.getHotelId());
            //set the rating to hotel
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setRatings(ratingList);
        logger.info(" User Details is {}",userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        //user.setUserId(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        User saveuser = userRepository.save(user);
        UserDto updatedDto = mapper.map(saveuser, UserDto.class);


        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> collects = users.stream().map(o -> objectMapper.convertValue(o, UserDto.class)).collect(Collectors.toList());
       // restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(), Rating[].class);
//        List<Rating> ratings=new ArrayList<>();
//        ratings.stream().map(rating ->
//        {
//           // List<Rating> allRatingUser = null;
//            for (User user : users) {
//                rating = (Rating) ratingServices.getAllRatingUser(user.getUserId());
//            }
//            return ratings;
//        }).collect(Collectors.toList());
       // Rating[] ratingsOfUser = new Rating[0];
        List<Rating> ratingList ;
        for(UserDto user:collects)
        {
            //ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(), Rating[].class);
            //logger.info(" Rating of users :{}",ratingsOfUser);
            ratingList=  ratingServices.getAllRatingUser(user.getUserId());
         //   List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
            // user.setRating(ratings);
            //User save = userRepository.save(user);
            //logger.info("{}",user);
            for(Rating rating: ratingList)
            {
                Hotel hotel = hotelServices.getHotel(rating.getHotelId());
                //  logger.info(" Response Status code is  {}",rating.getHotelId());
                //set the rating to hotel
                rating.setHotel(hotel);
            }
//            ratingList = Arrays.stream(ratingsOfUser).map(rating ->
//            {
//                //logger.info(" Hotel Id is  {}",rating.getHotelId().toString());
//                //api call of hotel
//                //url :  http://localhost:8082/hotels/4fa352b0-a984-4979-a88c-f416f88fcda3
//                //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//                Hotel hotel = hotelServices.getHotel(rating.getHotelId());
//                //  logger.info(" Response Status code is  {}",rating.getHotelId());
//                //set the rating to hotel
//                rating.setHotel(hotel);
//
//                //return the rating
//                return rating;
//            }).collect(Collectors.toList());
               user.setRatings(ratingList);
//            logger.info("Rating along with hotels{}",ratingList);
//            for (UserDto userDto : collects)
//            {
//                //  List<Rating> ratings = userDto.getRatings();
//                userDto.setRatings(ratingList);
//                //ratings.add(allRatingUser);
//                logger.info("UserDto {}",userDto.getRatings());
////            for(Rating rate : ratings)
////            {
////                userDto.setRatings(ratings);
////            }
//            }
            logger.info("Complete user data {} \n",user);
        }
        //logger.info(" Rating of users :{}",ratingsOfUser);
//        List<Rating> allRatingUser = Arrays.stream(ratingsOfUser).toList();
//        logger.info(" Rating of users :{}",allRatingUser);
//        for (Rating rating : allRatingUser) {
//          //  logger.info(" The rating List is {}", user.getName());
//            //   logger.info(" The rating List is {}", ratingServices.getAllRatingUser(user.getUserId()));
//             //allRatingUser = ratingServices.getAllRatingUser(user.getUserId());
//                
//            Hotel hotel = hotelServices.getHotel(rating.getHotelId());
//            logger.info(" The Hotel List is {}",  hotelServices.getHotel(rating.getHotelId()));
//            rating.setHotel(hotel);
//        }

       // logger.info("Complete user data is{}",users);


       // List<UserDto> collects = users.stream().map(o -> objectMapper.convertValue(o, UserDto.class)).collect(Collectors.toList());
//
//        logger.info("UserDyo data is {}",collects);
//        for (UserDto userDto : collects)
//        {
//          //  List<Rating> ratings = userDto.getRatings();
//            userDto.setRatings(ratingList);
//            //ratings.add(allRatingUser);
//            logger.info("UserDto {}",userDto.getRatings());
////            for(Rating rate : ratings)
////            {
////                userDto.setRatings(ratings);
////            }
//        }
//        logger.info("UserDto data is {}",collects);


//        logger.info("{}",ratings);
////        collects.stream().map(collect->{
////            for(Rating ratingtoList: ratings)
////            {
////                collect.setRatings((List<Rating>) ratingtoList);
////            }
////            return collects;
////        }).collect(Collectors.toList());
//        List<UserDto> collect = ratings.stream().map(o1 -> objectMapper.convertValue(o1, UserDto.class)).collect(Collectors.toList());
        return collects;
    }
}
