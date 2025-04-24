package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t")
    Optional<List<Tag>> getAllTags();

    @Query(value = "SELECT t.tag_name FROM tags t " +
            "INNER JOIN media_tags mt ON t.tag_id = mt.tag_id " +
            "WHERE mt.media_id = ?1", nativeQuery = true)
    Optional<List<String>> findTagsByMediaItemId(Long mediaId);
}
