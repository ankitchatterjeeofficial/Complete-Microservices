package com.user.service.Dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.service.Entities.Rating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto
{
    private String userId;

    @Size(min = 3,message = "Name Should be more than 3 characters!!")
    private String name;

    @NotBlank(message = "Email cant be Blank !!!")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$",message = "Invalid Email entered ex: username@email.in !!")
    private String email;



    @NotBlank(message = "Gender Cant be Blank !!")
    @Pattern(regexp = "(?:male|Male|female|Female|FEMALE|MALE|Not prefer to say)$",message = "Enter Male or Female or Not prefer to say ")
    private String gender;

    @NotBlank(message = "Write something about yourself!!!")
    private String about;


    private List<Rating> ratings=new ArrayList<>();
}
