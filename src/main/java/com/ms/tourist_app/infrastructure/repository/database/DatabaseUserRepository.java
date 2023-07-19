package com.ms.tourist_app.infrastructure.repository.database;

import com.ms.tourist_app.application.dai.UserRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("DatabaseUserRepository")
public interface DatabaseUserRepository extends UserRepository {

  @Override
  List<UserSubjectRelation> getListUserSubjectRelationBySubjectId(@Param("subjectId") Long subjectId);

  @Override
  int deleteByUserIdAndSubjectId(@Param("userId") String userId, @Param("subjectId") Long subjectId);

  @Override
  List<Subject> getListSubjectFromUserByUserId(@Param("userId") String userId);

  long countSearchUserInSubject(@Param("subjectId") Long subjectId, @Param("keyword") String keyword);

  @Override
  List<UserInSubjectDto> searchUserInSubject(@Param("subjectId") Long subjectId, @Param("keyword") String keyword,
                                             @Param("meta") PagingMeta meta);

}
