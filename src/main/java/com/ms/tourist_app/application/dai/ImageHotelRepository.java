package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.ImageHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageHotelRepository extends JpaRepository<ImageHotel,Long> {
}
