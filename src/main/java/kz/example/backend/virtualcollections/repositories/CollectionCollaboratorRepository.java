package kz.example.backend.virtualcollections.repositories;

import kz.example.backend.virtualcollections.entities.CollectionCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionCollaboratorRepository extends JpaRepository<CollectionCollaborator, Long> {

    @Query("SELECT c FROM CollectionCollaborator c WHERE c.collection.id = ?1")
    List<CollectionCollaborator> findCollectionCollaboratorByCollectionId(Long id);

}
