package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    @Query("select p from Province p where :name is null or (p.name like concat('%',:name,'%')) or " +
            "(p.slug like concat('%',:name,'%')) or " +
            "(p.slugWithSpace like concat('%',:name,'%')) or (p.slugWithoutSpace like concat('%',:name,'%'))")
//    @Query("select p from Province p where :name is null or " + "( p.name like concat('%[',:name,']%') and length(:name) <= 3 ) or" + "((( p.name like concat('%',:name,'%'))or(p.slug like concat('%',:name,'%')) or (p.slugWithSpace like concat('%',:name,'%')) or (p.slugWithoutSpace like concat('%',:name,'%')) )and length(:name) > 3 ) " + "ORDER BY p.name ASC")
    List<Province> findAllByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("select p from Province p where :name is null or (p.name like concat('%',:name,'%')) or " +
            "(p.slug like concat('%',:name,'%')) or " +
            "(p.slugWithSpace like concat('%',:name,'%')) or (p.slugWithoutSpace like concat('%',:name,'%'))")
    List<Province> findAllByKeyword(@Param("name") String name);

    @Query("select p from Province p where :name like concat('%', p.slugWithoutSpace, '%')")
    List<Province> findProvinceByNameContaining(@Param("name") String name);

    Province findAllByLongitudeAndLatitude(Double lon,Double lat);
    //    List<Province> findAllByNameRegex(@Param("name")String key,Pageable pageable);
    Province findByNameContainingIgnoreCase(String name);


}