package kz.example.backend.virtualcollections.entities;

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
@Table(name = "collection_likes", schema = "virtualcollections")
public class CollectionLike {
    @SequenceGenerator(name = "collection_likes_id_gen", sequenceName = "collection_comments_comment_id_seq", allocationSize = 1)
    @EmbeddedId
    private CollectionLikeId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("collectionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "liked_at")
    private OffsetDateTime likedAt;

}