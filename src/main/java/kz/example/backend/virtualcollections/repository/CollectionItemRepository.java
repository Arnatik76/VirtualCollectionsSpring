package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    @Query("SELECT i FROM CollectionItem i WHERE i.collection.id = ?1")
    Optional<List<CollectionItem>> getCollectionItemByUserId(Long id);

}
