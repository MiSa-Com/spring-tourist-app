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
@Entity
@Table(name = AppStr.Province.tableProvince)
public class Province extends BaseEntity {

    @Column(name = AppStr.Province.name)
    @Nationalized
    private String name;

    @Column(name = AppStr.Province.slug)
    private String slug;

    @Column(name = AppStr.Province.slugWithSpace)
    private String slugWithSpace;

    @Column(name = AppStr.Province.slugWithoutSpace)
    private String slugWithoutSpace;

    @OneToMany(mappedBy = AppStr.Province.tableProvince)
    @JsonIgnore
    private List<Address> addresses;

    @Column(name = AppStr.Province.divisionType)
    private String divisionType; // "city" or "province"

    @Column(name = AppStr.Province.longitude)
    private Double longitude;

    @Column(name = AppStr.Province.latitude)
    private Double latitude;
}
