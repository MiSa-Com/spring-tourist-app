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
//    @Query("select p from Province p where(:name is null or upper(p.name) like upper(concat('%', :name, '%')))")
    @Query(nativeQuery=true,value="select * from province where ?1 is null or name REGEXP ?1")
    List<Province> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
//    List<Province> findAllByNameRegex(@Param("name")String key,Pageable pageable);
    Province findByNameContainingIgnoreCase(String name);
}
