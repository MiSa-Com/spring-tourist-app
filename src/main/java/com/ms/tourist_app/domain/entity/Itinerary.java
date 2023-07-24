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

public class Itinerary extends BaseEntity {
    private String itinerary;

    private User user;

    @Enumerated(EnumType.STRING)
    private TravelMode travelMode;
}
