package kz.example.backend.virtualcollections.repositories;

import kz.example.backend.virtualcollections.entities.AchievementType;
import kz.example.backend.virtualcollections.entities.Collection;
import kz.example.backend.virtualcollections.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT c FROM Collection c WHERE c.user.id = ?1")
    List<Collection> getUserCollectionsByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT f.follower.id FROM UserFollow f WHERE f.followed.id = ?1)")
    List<User> getUserFollowersByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT f.followed.id FROM UserFollow f WHERE f.follower.id = ?1)")
    List<User> getUserFollowingByUserId(Long userId);

    @Query("SELECT a FROM AchievementType a WHERE a.id IN (SELECT ua.achievement.id FROM UserAchievement ua WHERE ua.user.id = ?1)")
    List<AchievementType> getUserAchievementsByUserId(Long userId);
}