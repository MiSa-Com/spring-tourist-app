package com.ms.tourist_app.domain.dto;

import com.ms.tourist_app.application.constants.AppStr;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO {

    private String name;


    private Integer code;


    private String divisionType;


    private String codeName;


    private String phoneCode;

    private Double longitude;

    private Double latitude;


}
