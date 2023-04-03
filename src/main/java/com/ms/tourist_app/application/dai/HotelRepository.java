package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Hotel findAllByTelephone(String telephone);
}
