package com.ms.tourist_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User extends BaseEntity {

    private String firstName;


    private String lastName;


    private LocalDate dateOfBirth;


    private String telephone;


    private String email;


    private String password;

    private List<Role> roles;


    private List<Hotel> hotels;


    private List<Booking> bookings;


    private List<CommentHotel> commentHotels;

    private List<CommentDestination> commentDestinations;

    private Address address;

    private List<Itinerary> favoriteItinerary;

    private List<Destination> favoriteDestination;

    public User(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}