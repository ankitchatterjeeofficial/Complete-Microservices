package com.user.service.Services;

import com.user.service.Entities.User;
import com.user.service.PayLoad.PageableResponse;
import com.user.service.Dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface UserServices
{
    UserDto saveUser(UserDto user);

   PageableResponse<UserDto>getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


    UserDto getUser( String userId);

    UserDto updateUser(UserDto userDto,String userId);

    void deleteUser(String userId);

    List<UserDto> searchUser(String keyword);
}
