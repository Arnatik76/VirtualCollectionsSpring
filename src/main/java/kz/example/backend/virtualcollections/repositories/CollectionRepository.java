package kz.example.backend.virtualcollections.repositories;

import kz.example.backend.virtualcollections.entities.Collection;
import kz.example.backend.virtualcollections.entities.CollectionCollaborator;
import kz.example.backend.virtualcollections.entities.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

}
