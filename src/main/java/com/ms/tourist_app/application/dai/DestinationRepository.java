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
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    @Query("select d from Destination d where d.destinationType = ?1")
    List<Destination> findAllByDestinationType(DestinationType destinationType, Pageable pageable);

    @Query("select d from Destination d where d.address = ?1")
    List<Destination> findAllByAddress(Address address);

    @Query("select d from Destination d " + "where (:address is null or d.address = :address)")
    List<Destination> findByAddress(@Param("address") Address address, Pageable pageable);

    @Query("select d from Destination d " + "where :address is null or d.address = :address and :name is null or d.name like :name or  d.slug like :name or  d.slugWithSpace like :name  or d.slugWithoutSpace like :name")
    List<Destination> filter(@Param("address") Address address, @Param("name") String name, Pageable pageable);

    @Query("SELECT d FROM Destination d ORDER BY d.createAt DESC")
    List<Destination> selectTopCreateAt(Pageable pageable);

    @Query("SELECT d FROM Destination d " +
            "JOIN d.address a " +
            "JOIN a.province p " +
            "WHERE :name is null or LOWER(d.name) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(d.slug) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(d.slugWithSpace) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(d.slugWithoutSpace) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(a.detailAddress) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(a.slug) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(a.slugWithSpace) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(a.slugWithoutSpace) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(p.name) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(p.slug) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(p.slugWithSpace) LIKE lower(concat('%', :name, '%')) " +
            "OR LOWER(p.slugWithoutSpace) LIKE lower(concat('%', :name, '%')) ")
    List<Destination> filter(@Param("name") String name, Pageable pageable);


    @Query("select d from Destination d where d.createBy = ?1")
    List<Destination> findAllByCreateBy(Long idUser);
}
