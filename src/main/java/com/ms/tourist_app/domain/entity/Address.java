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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.Address.tableAddress)
public class Address extends BaseEntity {

    @Column(name = AppStr.Address.detailAddress)
    @Nationalized
    private String detailAddress;


    @Column(name = AppStr.Address.slugWithSpace)
    private String slugWithSpace;


    @Column(name = AppStr.Address.slug)
    @Nationalized
    private String slug;

    @Column(name = AppStr.Address.slugWithoutSpace)
    private String slugWithoutSpace;

    @Column(name = AppStr.Address.longitude)
    private Double longitude;

    @Column(name = AppStr.Address.latitude)
    @Nationalized
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = AppStr.Address.idProvince)
    @JsonIgnore
    private Province province;
}
