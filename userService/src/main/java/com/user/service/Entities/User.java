package com.user.service.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "micro_users")
public class User
{

    @Id
    private String userId;


    @Column(name = "user_name",length = 100)
    private String name;

    @Column(name = "user_email",unique = true)
    private String email;

    private String gender;

    @Column(name = "user_about_details")
    private String about;

    @Transient
    private List<Rating> rating=new ArrayList<>();

}
