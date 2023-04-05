package com.ms.tourist_app.domain.entity;

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

    @Column(name = AppStr.Address.other)
    @Nationalized
    private String other;


    @Column(name = AppStr.Address.longitude)
    private Double longitude;


    @ManyToOne
    @JoinColumn(name = AppStr.Address.idProvince)
    private Province province;


    @Column(name = AppStr.Address.latitude)
    @Nationalized
    private Double latitude;


}
