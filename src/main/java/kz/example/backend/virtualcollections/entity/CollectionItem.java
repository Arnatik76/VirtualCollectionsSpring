package kz.example.backend.virtualcollections.entity;

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
@Table(name = "collection_items", schema = "virtualcollections")
public class CollectionItem {
    @SequenceGenerator(name = "collection_items_id_gen", sequenceName = "collection_comments_comment_id_seq", allocationSize = 1)
    @EmbeddedId
    private CollectionItemId id;

    @MapsId("collectionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "collection_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Collection collection;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MediaItem item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User addedByUser;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "added_at")
    private OffsetDateTime addedAt;

    @Column(name = "\"position\"")
    private Integer position;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;

}