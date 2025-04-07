package kz.example.backend.virtualcollections.repositories;

import kz.example.backend.virtualcollections.entities.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    @Query("SELECT i FROM CollectionItem i WHERE i.collection.id = ?1")
    List<CollectionItem> findCollectionItemsByCollectionId(Long id);

}
