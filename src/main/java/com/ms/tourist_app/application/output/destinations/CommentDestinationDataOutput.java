package com.ms.tourist_app.application.output.destinations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDestinationDataOutput {

    private Long idUser;

    private String nameUser;

    private Long idDestination;

    private String content;

    private Double rating;
}
