package com.ms.tourist_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.id.CommentHotelId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.CommentHotel.tableCommentHotel)
public class CommentHotel {
    @EmbeddedId
    private CommentHotelId commentHotelId;

    @ManyToOne
    @MapsId(AppStr.CommentHotel.idUserElement)
    @JoinColumn(name = AppStr.CommentHotel.idUser)
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId(AppStr.CommentHotel.idHotelElement)
    @JoinColumn(name = AppStr.CommentHotel.idHotel)
    @JsonIgnore
    private Hotel hotel;

    @Column(name = AppStr.CommentHotel.content)
    private String content;

    @Column(name = AppStr.CommentHotel.rating)
    private Double rating;


    @OneToMany(mappedBy = AppStr.CommentHotel.commentHotel)
    @JsonIgnore
    private List<ImageCommentHotel> imageCommentHotels;

}
