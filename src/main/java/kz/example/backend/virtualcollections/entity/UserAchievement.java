package kz.example.backend.virtualcollections.entity;

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
@Table(name = "user_achievements", schema = "virtualcollections")
public class UserAchievement {
    @SequenceGenerator(name = "user_achievements_id_gen", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    @EmbeddedId
    private UserAchievementId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("achievementId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "achievement_id", nullable = false)
    private AchievementType achievement;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "achieved_at")
    private OffsetDateTime achievedAt;

}