package com.ms.tourist_app.application.input.destinations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDestinationDataInput {

    private Long idDestination;

    private String content;

    private Double rating;
}
