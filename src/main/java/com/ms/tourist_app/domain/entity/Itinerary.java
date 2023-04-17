package com.ms.tourist_app.domain.entity;
import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.Itinerary.tableItinerary)
public class Itinerary extends BaseEntity {
    @Column(name = AppStr.Itinerary.itinerary)
    @Nationalized
    private String itinerary;
    @ManyToOne
    @JoinColumn(name = AppStr.Itinerary.idUser)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = AppStr.Itinerary.travelMode)
    private TravelMode travelMode;
}
