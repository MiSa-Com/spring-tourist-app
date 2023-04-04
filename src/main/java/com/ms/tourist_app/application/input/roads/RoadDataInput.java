package com.ms.tourist_app.application.input.roads;

import com.ms.tourist_app.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoadDataInput {
    private String road;
    private Long idUser;
}
