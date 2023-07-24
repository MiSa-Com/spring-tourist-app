package com.ms.tourist_app.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destination extends BaseEntity {


    private String name;


    private String slug;

    private String slugWithSpace;

    private String slugWithoutSpace;

    private String description;

    private DestinationType destinationType;


    private List<ImageDestination> imageDestinations;


    private Address address;

    private List<CommentDestination> commentDestinations;

    private List<User> favoriteUsers;
}
