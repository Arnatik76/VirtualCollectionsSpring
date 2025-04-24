package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {

    @Query("SELECT c FROM ContentType c")
    Optional<List<ContentType>> getAllContentTypes();
}
