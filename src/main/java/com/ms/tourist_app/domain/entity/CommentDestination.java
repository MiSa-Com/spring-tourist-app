package com.ms.tourist_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.id.CommentDestinationId;
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
@Table(name = AppStr.CommentDestination.tableCommentDestination)
public class CommentDestination {
    @EmbeddedId
    private CommentDestinationId commentDestinationId;

    @ManyToOne
    @MapsId(AppStr.CommentDestination.idUserElement)
    @JoinColumn(name = AppStr.CommentDestination.idUser)
    private User user;

    @ManyToOne
    @MapsId(AppStr.CommentDestination.idDestinationElement)
    @JoinColumn(name = AppStr.CommentDestination.idDestination)
    @JsonIgnore
    private Destination destination;


    @OneToMany(mappedBy = AppStr.CommentDestination.commentDestination)
    @JsonIgnore
    private List<ImageCommentDestination> imageCommentDestinations;

}
