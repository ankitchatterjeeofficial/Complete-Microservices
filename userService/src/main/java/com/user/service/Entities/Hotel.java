package com.user.service.Entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Hotel {

    private String hotelId;
    private String name;
    private String location;
    private String about;

}
