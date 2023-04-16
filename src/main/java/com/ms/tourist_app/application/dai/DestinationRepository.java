package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.DestinationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination,Long> {
    @Query("select d from Destination d")
    List<Destination> findAllDestinations();
    @Query("select d from Destination d where d.destinationType = ?1")
    List<Destination> findAllByDestinationType(DestinationType destinationType, Pageable pageable);

    @Query("select d from Destination d where d.address = ?1")
    List<Destination> findAllByAddress(Address address);

    @Query("select d from Destination d " +
            "where (:address is null or d.address = :address)")
    List<Destination> findByProvince(@Param("address") Address address, Pageable pageable);

    @Query("select d from Destination d " +
            "where (:address is null or d.address = :address) and (:name is null or d.name = :name)")
    List<Destination> filter(@Param("address") Address address,@Param("name") String name , Pageable pageable);


}
