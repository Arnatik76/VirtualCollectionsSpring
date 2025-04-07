package kz.example.backend.virtualcollections.repositories;

import kz.example.backend.virtualcollections.entities.CollectionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionCommentRepository extends JpaRepository<CollectionComment, Long> {

    @Query("SELECT c FROM CollectionComment c WHERE c.collection.id = ?1")
    List<CollectionComment> findCollectionItemsByCollectionId(Long id);
}
