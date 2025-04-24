package kz.example.backend.virtualcollections.service;

import kz.example.backend.virtualcollections.entity.AchievementType;
import kz.example.backend.virtualcollections.entity.Collection;
import kz.example.backend.virtualcollections.entity.User;

import kz.example.backend.virtualcollections.exception.ResourceAlreadyExistException;
import kz.example.backend.virtualcollections.exception.ResourceNotFoundException;

import kz.example.backend.virtualcollections.repository.UserFollowRepository;
import kz.example.backend.virtualcollections.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    public List<User> getAllUsers() {
        return userRepository.getAllUsers()
                .orElseThrow(() -> new ResourceNotFoundException("No users found"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void createUser(User user) {
        if (userRepository.existsByUsername((user.getUsername()))) {
            throw new ResourceAlreadyExistException("User already exists");
        }
        userRepository.save(user);
    }

    public void updateUser(Long id, User user) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public List<Collection> getUserCollections(Long userId) {
        return userRepository.getUserCollectionsByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Collections for user with ID " + userId + " not found"));
    }

    public List<User> getUserFollowers(Long userId) {
        return userRepository.getUserFollowersByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Followers for user with ID " + userId + " not found"));
    }

    public List<User> getUserFollowing(Long userId) {
        return userRepository.getUserFollowingByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Following for user with ID " + userId + " not found"));
    }

    public List<AchievementType> getUserAchievements(Long userId) {
        return userRepository.getUserAchievementsByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievements for user with ID " + userId + " not found"));
    }

    public void followUser(Long userId, Long followerId) {
        if (userFollowRepository.existsByUserIdAndFollowerId(userId, followerId)) {
            throw new ResourceAlreadyExistException("User with ID " + followerId + " already follows user with ID " + userId);
        }
        userFollowRepository.followUser(userId, followerId);
    }

    public void unfollowUser(Long userId, Long followerId) {
        if (!userFollowRepository.existsByUserIdAndFollowerId(userId, followerId)) {
            throw new ResourceNotFoundException("Follow relationship between user with ID " + followerId + " and user with ID " + userId + " not found");
        }
        userFollowRepository.unfollowUser(userId, followerId);
    }
}
