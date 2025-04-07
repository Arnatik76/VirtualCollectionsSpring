package kz.example.backend.virtualcollections.repositories;

import kz.example.backend.virtualcollections.entities.CollectionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionLikeRepository extends JpaRepository<CollectionLike, Long> {

    @Query("SELECT COUNT(c) FROM CollectionLike c WHERE c.collection.id = ?1")
    Long countCollectionLikesByCollectionId(Long id);
}
