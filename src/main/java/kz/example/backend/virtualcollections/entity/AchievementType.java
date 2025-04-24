package kz.example.backend.virtualcollections.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "achievement_types", schema = "virtualcollections")
public class AchievementType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "achievement_types_id_gen")
    @SequenceGenerator(name = "achievement_types_id_gen", sequenceName = "achievement_types_achievement_id_seq", allocationSize = 1)
    @Column(name = "achievement_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "requirement")
    private String requirement;

}