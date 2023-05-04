package com.ms.tourist_app.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.ImageDestination.tableImageDestination)
public class ImageDestination extends BaseEntity {
    @Column(name = AppStr.ImageDestination.link)
    @Length(max = 200000)
    private String link;


    @ManyToOne
    @JoinColumn(name = AppStr.ImageDestination.idDestination)
    @JsonIgnore
    private Destination destination;
}
