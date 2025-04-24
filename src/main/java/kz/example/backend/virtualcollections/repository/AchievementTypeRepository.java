package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.AchievementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementTypeRepository extends JpaRepository<AchievementType, Long> {

    @Query("SELECT a FROM AchievementType a")
    Optional<List<AchievementType>> getAllAchievementTypes();
}
