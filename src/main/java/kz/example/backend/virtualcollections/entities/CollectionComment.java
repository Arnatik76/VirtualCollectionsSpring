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
@Table(name = "collection_comments", schema = "virtualcollections")
public class CollectionComment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_comments_id_gen")
    @SequenceGenerator(name = "collection_comments_id_gen", sequenceName = "collection_comments_comment_id_seq", allocationSize = 1)
    @Column(name = "comment_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment_text", nullable = false, length = Integer.MAX_VALUE)
    private String commentText;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

}