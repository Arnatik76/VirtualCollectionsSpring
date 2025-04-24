package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.CollectionCollaborator;
import kz.example.backend.virtualcollections.entity.CollectionCollaboratorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionCollaboratorRepository extends JpaRepository<CollectionCollaborator, CollectionCollaboratorId> {

    @Query("SELECT c FROM CollectionCollaborator c WHERE c.collection.id = ?1")
    Optional<List<CollectionCollaborator>> findCollectionCollaboratorByCollectionId(Long id);
}
