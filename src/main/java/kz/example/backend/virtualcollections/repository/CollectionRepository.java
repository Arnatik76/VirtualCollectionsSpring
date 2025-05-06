package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.Collection;
import kz.example.backend.virtualcollections.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("SELECT c FROM Collection c")
    Optional<List<Collection>> getAllCollections();

    boolean existsByTitle(String title);
}
