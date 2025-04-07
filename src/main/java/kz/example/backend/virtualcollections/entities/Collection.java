package kz.example.backend.virtualcollections.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "collections", schema = "virtualcollections")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collections_id_gen")
    @SequenceGenerator(name = "collections_id_gen", sequenceName = "collections_collection_id_seq", allocationSize = 1)
    @Column(name = "collection_id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @ColumnDefault("false")
    @Column(name = "is_public")
    private Boolean isPublic;

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Integer viewCount;

}