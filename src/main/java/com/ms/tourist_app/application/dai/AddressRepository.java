package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select a from Address a where a.longitude = ?1 and a.latitude = ?2")
    Address findByLongitudeAndLatitude(Double longitude, Double latitude);

    @Query("select a from Address a " + "where (:keyword is null or upper(a.detailAddress) like upper(concat('%', :keyword, '%')) or upper(a.slug) like upper(concat('%', :keyword, '%')) or upper(a.slugWithSpace) like upper(concat('%', :keyword, '%'))or upper(a.slugWithoutSpace) like upper(concat('%', :keyword, '%')) )and(:province is null or a.province = :province)")
    List<Address> search(@Param("keyword") String keyword, @Param("province") Province province, Pageable pageable);


    @Query("select a from Address a " + "where (:keyword is null or upper(a.detailAddress) like upper(concat('%', :keyword, '%')) and( upper(a.slug) like upper(concat('%', :keyword, '%')) or upper(a.slugWithSpace) like upper(concat('%', :keyword, '%'))  or upper(a.slugWithoutSpace) like upper(concat('%', :keyword, '%')) ))")
    List<Address> searchWithoutProvince(@Param("keyword") String keyword, Pageable pageable);

    List<Address> findAllByProvince(Province province);

    @Query("select a from Address a where(:province is null or a.province = :province )or (:keyword is null or a.slug = :keyword) or (:keyword is null or a.slugWithSpace = :keyword) or (:keyword is null or a.slugWithoutSpace = :keyword) or(:keyword is null or a.detailAddress = :keyword)")
    List<Address> findAllByProvinceOrOtherOrDetailAddress(@Param("province") Province province, @Param("keyword") String keyword);

}
