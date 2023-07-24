package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.application.output.common.PagingMeta;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository {
    User findByEmail(String email);

    User findByTelephone(String telephone);

    List<User> search(String keyword, PagingMeta meta);

    List<User> findAllByAddress(Address address);

//    @Modifying
//    @Query("Update User u set u.favoriteDestination = :favoriteDest where u.id = :idUser")
//    void updateFavoriteDestination(@Param("idUser") Long userId,
//                                  @Param("favoriteDest") List<Destination> favoriteDest);
}
