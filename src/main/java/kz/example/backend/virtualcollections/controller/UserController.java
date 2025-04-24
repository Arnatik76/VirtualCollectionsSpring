package kz.example.backend.virtualcollections.controller;

import kz.example.backend.virtualcollections.entity.AchievementType;
import kz.example.backend.virtualcollections.entity.Collection;
import kz.example.backend.virtualcollections.entity.User;
import kz.example.backend.virtualcollections.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    GET /users - получение всех пользователей +
    GET /users/{id} - получение пользователя по ID +
    POST /users - создание нового пользователя +
    PUT /users/{id} - обновление пользователя +
    DELETE /users/{id} - удаление пользователя +
    GET /users/{id}/collections - коллекции пользователя +
    GET /users/{id}/followers - подписчики пользователя +
    GET /users/{id}/following - на кого подписан пользователь +
    GET /users/{id}/achievements - достижения пользователя +
    POST /api/users/{id}/follow - подписка на пользователя +
    DELETE /api/users/{id}/follow - отписка от пользователя +
*/

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(201).body("User created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{id}/collections")
    public ResponseEntity<List<Collection>> getUserCollections(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserCollections(id));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<User>> getUserFollowers(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserFollowers(id));
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<User>> getUserFollowing(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserFollowing(id));
    }

    @GetMapping("/{id}/achievements")
    public ResponseEntity<List<AchievementType>> getUserAchievements(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserAchievements(id));
    }

    @PostMapping("/{userId}/follow/{followerId}")
    public ResponseEntity<String> followUser(@PathVariable("userId") Long id, @PathVariable("followerId") Long followerId) {
        userService.followUser(id, followerId);
        return ResponseEntity.status(201).body("User followed successfully");
    }

    @DeleteMapping("/{userId}/follow/{followerId}")
    public ResponseEntity<String> unfollowUser(@PathVariable("userId") Long id, @RequestBody Long followerId) {
        userService.unfollowUser(id, followerId);
        return ResponseEntity.ok("User unfollowed successfully");
    }
}
