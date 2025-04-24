package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.CollectionLike;
import kz.example.backend.virtualcollections.entity.CollectionLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionLikeRepository extends JpaRepository<CollectionLike, CollectionLikeId> {

    @Query("SELECT COUNT(c) FROM CollectionLike c WHERE c.collection.id = ?1")
    Optional<Long> countCollectionLikesByCollectionId(Long id);

    @Query("SELECT c FROM CollectionLike c WHERE c.collection.id = ?1")
    Optional<List<CollectionLike>> findCollectionLikesByCollectionId(Long id);
}
