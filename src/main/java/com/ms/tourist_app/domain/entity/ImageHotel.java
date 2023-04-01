package com.ms.tourist_app.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.ImageHotel.tableImageHotel)
public class ImageHotel extends BaseEntity {
    @Column(name = AppStr.ImageDestination.link)
    private String link;


    @ManyToOne
    @JoinColumn(name = AppStr.ImageHotel.idHotel)
    @JsonIgnore
    private Hotel hotel;
}
