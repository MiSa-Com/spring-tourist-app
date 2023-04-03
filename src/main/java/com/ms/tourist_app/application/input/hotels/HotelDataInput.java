package com.ms.tourist_app.application.input.hotels;

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
public class HotelDataInput {
    private String name;
    private String description;
    private String telephone;
    private Long idAddress;
    private Long idUser;
    private List<MultipartFile> images;
}
