package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Hotel findAllByTelephone(String telephone);

    @Query("select h from Hotel h where(:name is null or upper(h.name) like upper(concat('%', :name, '%')) or h.address = :address)")
    List<Hotel> findAllByNameIsContainingIgnoreCaseAndAddress(@Param("name") String name, @Param("address") Address address);
}
