package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province,Long> {
    @Query("select p from Province p where(:name is null or upper(p.name) like upper(concat('%', :name, '%')))")
    List<Province> findAllByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    Province findByNameContainingIgnoreCase(String name);
}
