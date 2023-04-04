package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("select a from Address a where a.longitude = ?1 and a.latitude = ?2")
    Address findByLongitudeAndLatitude(Double longitude, Double latitude);

    @Query("select a from Address a " +
            "where (:keyword is null or a.province like concat('%', :keyword, '%') " +
            "or a.detailAddress like concat('%', :keyword, '%'))" +
            "or a.other like concat('%', :keyword, '%')")
    List<Address> search(@Param("keyword") String keyword, Pageable pageable);


}
