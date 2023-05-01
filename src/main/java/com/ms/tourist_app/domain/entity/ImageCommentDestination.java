package com.ms.tourist_app.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.ImageCommentDestination.tableImageComment)
public class ImageCommentDestination extends BaseEntity {
    @Column(name = AppStr.ImageCommentDestination.link)
    private String link;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = AppStr.ImageCommentDestination.idUser),
            @JoinColumn(name = AppStr.ImageCommentDestination.idDestination),
            @JoinColumn(name = AppStr.ImageCommentDestination.idCommentDestination)
    })
    @JsonIgnore
    private CommentDestination commentDestination;
}
