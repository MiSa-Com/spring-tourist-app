package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Province;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
class ProvinceRepositoryUnitTest {

    @Autowired
    private ProvinceRepository provinceRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        provinceRepository.deleteAll();
    }

    @Test
    void findAllByNameContainingIgnoreCase_Success() {
        // given
        Province province = new Province("Hanoi", "Hanoi", "Hanoi", "Hanoi",
                                List.of(), "city", 21.028511, 105.804817);
        provinceRepository.save(province);
        int nbPage = 0;
        int nbElement = 1;
        // when
        List<Province> listProvinces = provinceRepository.findAllByNameContainingIgnoreCase("Hanoi", PageRequest.of(nbPage, nbElement));
        // assert
        assertThat("Province name is not correct", listProvinces.get(0).getName().equals("Hanoi"));
    }
}