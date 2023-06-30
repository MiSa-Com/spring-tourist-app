package com.ms.tourist_app.domain.entity.id;

import com.ms.tourist_app.application.constants.AppStr;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CommentDestinationId implements Serializable {

    @Column(name = AppStr.CommentDestination.idCommentDestination)
    private Long id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);


    @Column(name = AppStr.CommentDestination.idUser)
    private Long idUser;

    @Column(name = AppStr.CommentDestination.idDestination)
    private Long idDestination;

    public CommentDestinationId(Long idUser, Long idDestination) {
        this.idUser = idUser;
        this.idDestination = idDestination;
    }
}
