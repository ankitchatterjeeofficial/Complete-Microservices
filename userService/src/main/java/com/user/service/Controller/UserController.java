package com.user.service.Controller;

import com.user.service.Dtos.UserDto;
import com.user.service.Entities.User;
import com.user.service.PayLoad.ApiResponseMessage;
import com.user.service.PayLoad.PageableResponse;
import com.user.service.Services.Impl.UserServicesImpl;
import com.user.service.Services.UserServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userDto1 = userServices.saveUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public  ResponseEntity<UserDto> updateUser(@PathVariable String userId,@Valid @RequestBody UserDto userDto)
    {
        UserDto updateUser = userServices.updateUser(userDto, userId);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "aesc",required = false) String sortDir
    )
    {
        return new ResponseEntity<>(userServices.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
    {
        userServices.deleteUser(userId);
        ApiResponseMessage message1 = ApiResponseMessage.builder()
                .message("User is Successfully Deleted!!!!")
                .success(true).status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message1,HttpStatus.OK);
    }

    int retryCount=1;
    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
   // @Retry(name = "ratingHotelRetry",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public  ResponseEntity<UserDto> getUser(@PathVariable String userId)
    {
        logger.info(" Retry Count is :{}",retryCount);
        if(retryCount==3)
            retryCount=1;
        else {
            retryCount++;
        }
        return new ResponseEntity<>(userServices.getUser(userId),HttpStatus.OK);
    }

    //creating fallback method of circuit Breaker

    public ResponseEntity<UserDto> ratingHotelFallback(String userId,Exception ex)
    {

        UserDto userDto = UserDto.builder()
                .userId("dummyId1")
                .email("dummy@dev.in")
                .name("dummy")
                .about("This is Dummy User")
                .gender("MALE")
                .build();
       // logger.info("Fallback Method is Executed because Service is Down :",ex.getMessage());

        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords)
    {
        return new ResponseEntity<>(userServices.searchUser(keywords),HttpStatus.OK);
    }

}
