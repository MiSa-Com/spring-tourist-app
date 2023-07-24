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
public class Address extends BaseEntity {

    private String detailAddress;


    private String slugWithSpace;


    private String slug;

    private String slugWithoutSpace;

    private Double longitude;

    private Double latitude;

    private Province province;

    @PreRemove
    private void setNullProvince(){
        this.province = null;
    }
}
