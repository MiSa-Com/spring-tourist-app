package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.DestinationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationTypeRepository extends JpaRepository<DestinationType,Long> {
    @Query("select d from DestinationType d where upper(d.name) like upper(concat('%', ?1, '%'))")
    List<DestinationType> findAllByNameContainingIgnoreCase(String name, Pageable pageable);


    @Query("select d from DestinationType d where(?1 is null or d.name like concat('%',?1, '%'))")
    DestinationType findAllByNameContainingIgnoreCase(String name);
}
