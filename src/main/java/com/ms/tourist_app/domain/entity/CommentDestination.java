package com.ms.tourist_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
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
public class CommentDestination {

    private User user;

    private Destination destination;

    private String content;

    private Double rating;

    private List<ImageCommentDestination> imageCommentDestinations;

    public CommentDestination( User user, Destination destination, String content, Double rating) {

        this.user = user;
        this.destination = destination;
        this.content = content;
        this.rating = rating;
    }
}
