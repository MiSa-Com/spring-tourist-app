package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.CommentDestination;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.id.CommentDestinationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDestinationRepository extends JpaRepository<CommentDestination, CommentDestinationId> {
    List<CommentDestination> findAllByDestination(Destination destination);
}
