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
@Table(name = AppStr.ImageCommentHotel.tableImageComment)
public class ImageCommentHotel extends BaseEntity {
    @Column(name = AppStr.ImageCommentHotel.link)
    private String link;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = AppStr.ImageCommentHotel.idUser),
            @JoinColumn(name = AppStr.ImageCommentHotel.idHotel),
    })
    @JsonIgnore
    private CommentHotel commentHotel;
}
