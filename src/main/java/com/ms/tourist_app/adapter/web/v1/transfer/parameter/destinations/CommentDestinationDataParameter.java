package com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDestinationDataParameter {

    private Long idDestination;

    private String content;

    private Double rating;
}
