package com.ms.tourist_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.id.BookingId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @EmbeddedId
    private BookingId bookingId;

    @ManyToOne
    @MapsId(AppStr.Booking.idUserElement)
    @JoinColumn(name = AppStr.Booking.idUser)
    @JsonIgnore
    private User user;


    @ManyToOne
    @MapsId(AppStr.Booking.idRoomElement)
    @JoinColumn(name = AppStr.Booking.idRoom)
    @JsonIgnore
    private Room room;


    @Column(name = AppStr.Booking.checkIn)
    private LocalDateTime checkIn;


    @Column(name = AppStr.Booking.checkOut)
    private LocalDateTime checkOut;


    @Column(name = AppStr.Booking.totalPrice)
    private Double totalPrice;
}
