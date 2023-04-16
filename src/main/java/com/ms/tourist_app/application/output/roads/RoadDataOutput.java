package com.ms.tourist_app.application.output.roads;

import com.ms.tourist_app.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoadDataOutput {
    private String road;
    private User user;
}

