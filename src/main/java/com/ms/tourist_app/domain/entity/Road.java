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
@Table(name = AppStr.Road.tableRoad)
public class Road extends BaseEntity {
    @Column(name = AppStr.Road.road)
    @Nationalized
    private String road;
    @ManyToOne
    @JoinColumn(name = AppStr.Road.idUser)
    private User user;
}
