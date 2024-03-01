package com.hotel.service.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.context.annotation.Scope;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="micro_hotels")
public class Hotel
{

    @Id
    @Column(name = "hotel_id")
    private String hotelId;
    @Column(name = "hotel_Name")
    private String name;

    @Column(name = "hotel_address")
    private String location;

    private String about;

}
