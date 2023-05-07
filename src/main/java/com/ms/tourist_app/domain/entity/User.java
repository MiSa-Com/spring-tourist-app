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
@Entity
@Table(name = AppStr.User.tableUser)
public class User extends BaseEntity {

    @Column(name = AppStr.User.firstName)
    @Nationalized
    private String firstName;


    @Column(name = AppStr.User.lastName)
    @Nationalized
    private String lastName;


    @Column(name = AppStr.User.dateOfBirth)
    private LocalDate dateOfBirth;


    @Column(name = AppStr.User.telephone)
    private String telephone;


    @Column(name = AppStr.User.email)
    private String email;


    @Column(name = AppStr.User.password)
    private String password;

    @ManyToMany(mappedBy = AppStr.User.tableUser)
    @JsonIgnore
    private List<Role> roles;


    @OneToMany(mappedBy = AppStr.User.joinTableUser,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Hotel> hotels;


    @OneToMany(mappedBy = AppStr.User.joinTableUser)
    @JsonIgnore
    private List<Booking> bookings;


    @OneToMany(mappedBy = AppStr.User.joinTableUser)
    @JsonIgnore
    private List<CommentHotel> commentHotels;

    @OneToMany(mappedBy = AppStr.User.joinTableUser)
    @JsonIgnore
    private List<CommentDestination> commentDestinations;

    @ManyToOne
    @JoinColumn(name = AppStr.User.idAddress)
    @JsonIgnore
    private Address address;

    @OneToMany(mappedBy = AppStr.User.joinTableUser)
    @JsonIgnore
    private List<Itinerary> favoriteItinerary;

    @OneToMany(targetEntity = Destination.class)
    private List<Destination> favoriteDestination;

    public User(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}