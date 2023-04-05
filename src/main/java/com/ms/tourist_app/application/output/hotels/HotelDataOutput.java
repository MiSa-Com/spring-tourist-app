package com.ms.tourist_app.application.output.hotels;

import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.CommentHotel;
import com.ms.tourist_app.domain.entity.ImageHotel;
import com.ms.tourist_app.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDataOutput {
    private Long id;
    private Long createBy;
    private LocalDateTime createAt;
    private Long updateBy;
    private LocalDateTime updateAt;
    private String name;
    private String description;
    private String telephone;
    private Address address;
    private User user;
    private List<ImageHotel> images;
    private List<CommentHotel> commentHotel;


}
