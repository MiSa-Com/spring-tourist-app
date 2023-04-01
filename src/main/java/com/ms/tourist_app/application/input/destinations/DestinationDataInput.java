package com.ms.tourist_app.application.input.destinations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDataInput {
    private String name;
    private String description;
    private Long idDestinationType;
    private Long idAddress;
    private List<MultipartFile> images;
}
