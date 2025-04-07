package kz.example.backend.virtualcollections.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tags", schema = "virtualcollections")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_gen")
    @SequenceGenerator(name = "tags_id_gen", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    @Column(name = "tag_id", nullable = false)
    private Integer id;

    @Column(name = "tag_name", nullable = false, length = 50)
    private String tagName;

}