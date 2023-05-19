package com.ms.tourist_app.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.Destination.tableDestination)
public class Destination extends BaseEntity {


    @Column(name = AppStr.Destination.name)
    @Nationalized
    private String name;


    @Column(name = AppStr.Destination.slug)
    private String slug;

    @Column(name = AppStr.Destination.slugWithSpace)
    private String slugWithSpace;

    @Column(name = AppStr.Destination.slugWithoutSpace)
    private String slugWithoutSpace;

    @Column(name = AppStr.Destination.description)
    @Nationalized
    @Length(max = 200000)
    private String description;

    @ManyToOne
    @JoinColumn(name = AppStr.Destination.idTypeDestination)
    @JsonIgnore
    private DestinationType destinationType;


    @OneToMany(mappedBy = AppStr.Destination.tableDestination)
    private List<ImageDestination> imageDestinations;


    @ManyToOne
    @JoinColumn(name = AppStr.Destination.idAddress)
    @JsonIgnore
    private Address address;

    @OneToMany(mappedBy = AppStr.Destination.tableDestination)
    @JsonIgnore
    private List<CommentDestination> commentDestinations;

    @ManyToMany
    @JoinTable(name = AppStr.Destination.joinTableDestinationUser)
    @JsonIgnore
    private List<User> favoriteUsers;


}
