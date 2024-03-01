package com.rating.service.Dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto
{
    private String ratingId;
    private String userId;
    private String hotelId;


   @Max(value = 5,message = "Provide Rating Between 1-5, 1 -lowest and 5 -highest ")
   @Min(value = 1,message = "Provide Rating Between 1-5, 1 -lowest and 5 -highest ")
    private int rating;

    @NotBlank(message = "Kindly provide Remarks its definitely helps us to Improve our Service !!!")
    private String remark;
}
