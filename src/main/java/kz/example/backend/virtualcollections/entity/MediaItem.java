package kz.example.backend.virtualcollections.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "media_items", schema = "virtualcollections")
public class MediaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_items_id_gen")
    @SequenceGenerator(name = "media_items_id_gen", sequenceName = "media_items_item_id_seq", allocationSize = 1)
    @Column(name = "item_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ContentType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "creator")
    private String creator;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "external_url")
    private String externalUrl;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "added_at")
    private OffsetDateTime addedAt;

}