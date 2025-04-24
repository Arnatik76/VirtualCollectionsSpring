package kz.example.backend.virtualcollections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import kz.example.backend.virtualcollections.entity.Collection;
import kz.example.backend.virtualcollections.entity.User;
import kz.example.backend.virtualcollections.entity.AchievementType;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u")
    Optional<List<User>> getAllUsers();

    @Query("SELECT c FROM Collection c WHERE c.user.id = ?1")
    Optional<List<Collection>> getUserCollectionsByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT f.follower.id FROM UserFollow f WHERE f.followed.id = ?1)")
    Optional<List<User>> getUserFollowersByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT f.followed.id FROM UserFollow f WHERE f.follower.id = ?1)")
    Optional<List<User>> getUserFollowingByUserId(Long userId);

    @Query("SELECT a FROM AchievementType a WHERE a.id IN (SELECT ua.achievement.id FROM UserAchievement ua WHERE ua.user.id = ?1)")
    Optional<List<AchievementType>> getUserAchievementsByUserId(Long userId);

    boolean existsByUsername(String username);
}
