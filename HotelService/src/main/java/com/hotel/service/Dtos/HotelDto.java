package com.hotel.service.Dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDto
{

    private String hotelId;

    @Size(min = 5,message = "Hotel Name Should be more than 4 characters!!")
    private String name;

    @Size(min = 6,message = "Location Should be more than 5 characters!!")
    private String location;

    @NotBlank(message = "Write something about Hotel !!!")
    private String about;
}
