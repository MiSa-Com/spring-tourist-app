package com.ms.tourist_app.domain.entity.id;

import com.ms.tourist_app.application.constants.AppStr;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CommentDestinationId implements Serializable {

    @Column(name = AppStr.CommentDestination.idUser)
    private Long idUser;

    @Column(name = AppStr.CommentDestination.idDestination)
    private Long idDestination;
}
