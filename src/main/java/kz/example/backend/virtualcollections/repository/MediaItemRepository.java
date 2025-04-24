package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.MediaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaItemRepository extends JpaRepository<MediaItem, Long> {

    @Query("SELECT m FROM MediaItem m")
    Optional<List<MediaItem>> getAllMediaItems();

    @Modifying
    @Transactional
    @Query(value = "WITH tag_insert AS (" +
            "    INSERT INTO virtualcollections.tags (tag_name) " +
            "    VALUES (?2) " +
            "    ON CONFLICT (tag_name) DO UPDATE SET tag_name = EXCLUDED.tag_name " +
            "    RETURNING tag_id" +
            ") " +
            "INSERT INTO virtualcollections.media_tags (media_id, tag_id) " +
            "SELECT ?1, tag_id FROM tag_insert " +
            "ON CONFLICT (media_id, tag_id) DO NOTHING", nativeQuery = true)
    void addTagToMediaItem(Long mediaId, String tagName);
}
