package com.ms.tourist_app.application.output.destinations;

import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.CommentDestination;
import com.ms.tourist_app.domain.entity.DestinationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDataOutput {
    private Integer id;
    private String name;
    private String description;
    private DestinationType destinationType;
    private Address address;
    private List<String> images;
    private List<CommentDestinationDataOutput> commentDestinations;
}
