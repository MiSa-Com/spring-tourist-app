package com.ms.tourist_app.domain.entity.id;

import com.ms.tourist_app.application.constants.AppStr;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookingId implements Serializable {

    @Column(name = AppStr.Booking.idUser)
    private Long idUser;

    @Column(name = AppStr.Booking.idRoom)
    private Long idRoom;

}
