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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = AppStr.Hotel.tableHotel)
@Entity
public class Hotel extends BaseEntity {
    @Column(name = AppStr.Hotel.name)
    @Nationalized
    private String name;


    @Column(name = AppStr.Hotel.description)
    @Nationalized
    private String description;


    @Column(name = AppStr.Hotel.telephone)
    private String telephone;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = AppStr.Hotel.idUser)
    private User user;

    @OneToMany(mappedBy = AppStr.Hotel.tableHotel,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ImageHotel> imageHotels;

    @OneToMany(mappedBy = AppStr.Hotel.tableHotel)
    @JsonIgnore
    private List<Room> rooms;

    @OneToMany(mappedBy = AppStr.Hotel.tableHotel)
    @JsonIgnore
    private List<CommentHotel> commentHotels;


    @ManyToOne
    @JoinColumn(name = AppStr.Hotel.idAddress)
    @JsonIgnore
    private Address address;
}
