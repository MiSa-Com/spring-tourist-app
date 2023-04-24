package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.CurrentWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentWeatherRepository extends JpaRepository<CurrentWeather,Long> {
    List<CurrentWeather> findAllByProvince(String province);
    CurrentWeather findByIdProvince(Long idProvince);
}
