package kz.example.backend.virtualcollections.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CollectionLikeId implements Serializable {
    @Serial
    private static final long serialVersionUID = -994276153904150601L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "collection_id", nullable = false)
    private Long collectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CollectionLikeId entity = (CollectionLikeId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.collectionId, entity.collectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, collectionId);
    }

}