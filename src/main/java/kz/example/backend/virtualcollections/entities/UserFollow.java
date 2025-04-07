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
@Table(name = "user_follows", schema = "virtualcollections")
public class UserFollow {
    @SequenceGenerator(name = "user_follows_id_gen", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    @EmbeddedId
    private UserFollowId id;

    @MapsId("followerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @MapsId("followedId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "followed_at")
    private OffsetDateTime followedAt;

}