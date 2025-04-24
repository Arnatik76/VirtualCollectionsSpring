package kz.example.backend.virtualcollections.repository;

import kz.example.backend.virtualcollections.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO java.virtualcollections.user_follows (follower_id, followed_id) VALUES (?1, ?2)", nativeQuery = true)
    void followUser(Long followerId, Long followedId);

    @Modifying
    @Query(value = "DELETE FROM java.virtualcollections.user_follows identifier WHERE follower_id = ?1 AND followed_id = ?2", nativeQuery = true)
    void unfollowUser(Long followerId, Long followedId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserFollow u WHERE u.follower.id = ?1 AND u.followed.id = ?2")
    boolean existsByUserIdAndFollowerId(Long userId, Long followerId);
}