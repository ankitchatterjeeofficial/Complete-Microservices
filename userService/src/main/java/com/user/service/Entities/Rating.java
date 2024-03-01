package com.user.service.Entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Rating
{
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String remark;

    private Hotel hotel;
}
