package com.ms.tourist_app.application.service.imp;

import com.github.slugify.Slugify;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.input.provinces.GetListProvinceDataInput;
import com.ms.tourist_app.application.input.provinces.ProvinceDataInput;
import com.ms.tourist_app.application.mapper.ProvinceMapper;
import com.ms.tourist_app.application.output.provinces.ProvinceDataOutput;
import com.ms.tourist_app.application.service.ProvinceService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.domain.entity.Province;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProvinceServiceImpUnitTest {
    @Mock
    private ProvinceRepository provinceRepository;
    @Mock
    private JwtUtil jwtUtil;

    private final ProvinceMapper provinceMapper = Mappers.getMapper(ProvinceMapper.class);

    private ProvinceService provinceService;
    @BeforeEach
    void setUp() {
        provinceService = new ProvinceServiceImp(provinceRepository, jwtUtil);
    }

    @AfterEach
    void tearDown() {
        provinceRepository.deleteAll();
    }

    @Test
    void canGetListProvinces() {
        // given
        GetListProvinceDataInput input = new GetListProvinceDataInput("Hanoi", 0, 5);

        // when
        provinceService.getListProvinceDataOutput(input);

        // then
        verify(provinceRepository).findAllByNameContainingIgnoreCase("Hanoi", PageRequest.of(0, 5));
    }

    @Test
    void canAddProvince() {
        // given
        ProvinceDataInput input = new ProvinceDataInput("Hà Nội");

        // when
        ProvinceDataOutput actual = provinceService.createProvince(input);

        // then
        ArgumentCaptor<Province> provinceArgumentCaptor = ArgumentCaptor.forClass(Province.class);
        verify(provinceRepository).save(provinceArgumentCaptor.capture());

        Province province = provinceArgumentCaptor.getValue();
        ProvinceDataOutput expected = provinceMapper.toProvinceDataOutput(province);
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void cannotAddProvince() {
        // given
        ProvinceDataInput input = new ProvinceDataInput("Hà Nội");
        given(provinceRepository.findByNameContainingIgnoreCase(input.getName())).willReturn(new Province());
        // when

        // then
        assertThatThrownBy(() -> provinceService.createProvince(input))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("province Duplicate");

        verify(provinceRepository, never()).save(any());
    }
}