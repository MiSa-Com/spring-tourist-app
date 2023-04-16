package com.ms.tourist_app.adapter.web.v1.transfer.parameter.addresses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDataParameter {

    private Long idProvince;

    @NotBlank(message = "detail address must not null")
    private String detailAddress;
}
