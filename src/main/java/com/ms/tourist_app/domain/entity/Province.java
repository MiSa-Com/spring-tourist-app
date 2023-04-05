package com.ms.tourist_app.domain.entity;


import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @OneToMany(mappedBy = AppStr.Province.tableProvince)
    private List<Address> addresses;
}
