package com.ms.tourist_app.domain.entity;

import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrentWeather {

    private Long id;

    private Long idProvince;

    private String province;

    private String dateTime;

    private Float temp;

    private Float feelsLike;

    private Float tempMin;

    private Float tempMax;

    private Integer humidity;

    private String main;

    private String description;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().getMostSignificantBits() & Integer.MAX_VALUE;;
        }
    }
}
