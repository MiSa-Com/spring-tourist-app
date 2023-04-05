package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadRepository extends JpaRepository<Road,Long> {
}
