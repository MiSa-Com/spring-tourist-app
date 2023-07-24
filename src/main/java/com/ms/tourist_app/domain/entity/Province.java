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
public class Province extends BaseEntity {

    private String name;

    private String slug;

    private String slugWithSpace;

    private String slugWithoutSpace;

    private List<Address> addresses;

    private String divisionType; // "city" or "province"

    private Double longitude;

    private Double latitude;
}
