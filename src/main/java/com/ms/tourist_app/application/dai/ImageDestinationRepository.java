package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.ImageDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDestinationRepository extends JpaRepository<ImageDestination,Long> {
    @Query("select i from ImageDestination i where i.destination = ?1")
    List<ImageDestination> findAllByDestination(Destination destination);
}
