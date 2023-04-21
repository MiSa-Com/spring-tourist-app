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
@Table(name = AppStr.Weather.tableWeather)
@Entity
public class WeatherForcast {

    @Id
    private Long id;

    @Column(name = AppStr.Weather.idProvince)
    private Long idProvince;

    @Column(name = AppStr.Weather.province)
    @Nationalized
    private String province;

    @Column(name = AppStr.Weather.dateTime)
    private String dateTime;

    @Column(name = AppStr.Weather.temp)
    private Float temp;

    @Column(name = AppStr.Weather.feelLike)
    private Float feelsLike;

    @Column(name = AppStr.Weather.tempMin)
    private Float tempMin;

    @Column(name = AppStr.Weather.tempMax)
    private Float tempMax;

    @Column(name = AppStr.Weather.humidity)
    private Integer humidity;

    @Column(name = AppStr.Weather.main)
    @Nationalized
    private String main;

    @Column(name = AppStr.Weather.description)
    @Nationalized
    private String description;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().getMostSignificantBits() & Integer.MAX_VALUE;;
        }
    }
}
