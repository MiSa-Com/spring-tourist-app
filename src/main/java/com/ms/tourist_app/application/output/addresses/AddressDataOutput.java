package com.ms.tourist_app.application.output.addresses;

import com.ms.tourist_app.application.output.provinces.ProvinceDataOutput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDataOutput {
    private Long id;
    private Long createBy;
    private LocalDateTime createAt;
    private Long updateBy;
    private LocalDateTime updateAt;
    private String slug;
    private Double longitude;
    private Double latitude;
    private ProvinceDataOutput province;
    private String detailAddress;
}
