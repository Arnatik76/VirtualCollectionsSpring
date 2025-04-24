package kz.example.backend.virtualcollections.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "content_types", schema = "virtualcollections")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_types_id_gen")
    @SequenceGenerator(name = "content_types_id_gen", sequenceName = "content_types_type_id_seq", allocationSize = 1)
    @Column(name = "type_id", nullable = false)
    private Integer id;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Column(name = "type_icon", length = 100)
    private String typeIcon;

}