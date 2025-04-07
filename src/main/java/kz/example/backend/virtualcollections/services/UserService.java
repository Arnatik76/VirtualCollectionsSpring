package kz.example.backend.virtualcollections.services;

import kz.example.backend.virtualcollections.entities.AchievementType;
import kz.example.backend.virtualcollections.entities.Collection;
import kz.example.backend.virtualcollections.entities.User;
import kz.example.backend.virtualcollections.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Collection> getUserCollections(Long userId) {
        return userRepository.getUserCollectionsByUserId(userId);
    }

    public List<User> getUserFollowers(Long userId) {
        return userRepository.getUserFollowersByUserId(userId);
    }

    public List<User> getUserFollowing(Long userId) {
        return userRepository.getUserFollowingByUserId(userId);
    }

    public List<AchievementType> getUserAchievements(Long userId) {
        return userRepository.getUserAchievementsByUserId(userId);
    }
}
