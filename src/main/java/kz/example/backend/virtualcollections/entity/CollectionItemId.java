package kz.example.backend.virtualcollections.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CollectionItemId implements Serializable {
    private static final long serialVersionUID = 6263890364575485209L;
    @Column(name = "collection_id", nullable = false)
    private Long collectionId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CollectionItemId entity = (CollectionItemId) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.collectionId, entity.collectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, collectionId);
    }

}