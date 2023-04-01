package com.ms.tourist_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.Room.tableRoom)
public class Room extends BaseEntity {

    @Column(name = AppStr.Room.name)
    private String name;


    @Column(name = AppStr.Room.price)
    private Double price;


    @Column(name = AppStr.Room.superficie)
    private Double superficie;

    @Column(name = AppStr.Room.destination)
    private String description;


    @Column(name = AppStr.Room.calendar)
    private Calendar calendar;


    @Column(name = AppStr.Room.status)
    private Boolean status;


    @ManyToOne
    @JoinColumn(name = AppStr.Room.idHotel)
    @JsonIgnore
    private Hotel hotel;


    @OneToMany(mappedBy = AppStr.Room.room)
    @JsonIgnore
    private List<Booking> bookings;

}
