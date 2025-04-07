package kz.example.backend.virtualcollections.entities;

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
public class MediaTagId implements Serializable {
    private static final long serialVersionUID = -7406992091032083643L;
    @Column(name = "media_id", nullable = false)
    private Integer mediaId;

    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MediaTagId entity = (MediaTagId) o;
        return Objects.equals(this.tagId, entity.tagId) &&
                Objects.equals(this.mediaId, entity.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, mediaId);
    }

}