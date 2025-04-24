package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.CollectionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionCommentRepository extends JpaRepository<CollectionComment, Long> {

    @Query("SELECT c FROM CollectionComment c WHERE c.collection.id = ?1")
    Optional<List<CollectionComment>> findCollectionItemsByCollectionId(Long id);
}
