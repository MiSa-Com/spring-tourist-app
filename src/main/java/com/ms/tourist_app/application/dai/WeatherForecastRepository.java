package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.WeatherForcast;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherForecastRepository extends JpaRepository<WeatherForcast,Long> {
    List<WeatherForcast> findAllByIdProvince(Long idProvince);

}

