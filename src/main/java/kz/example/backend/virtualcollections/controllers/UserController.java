package kz.example.backend.virtualcollections.controllers;

import kz.example.backend.virtualcollections.entities.AchievementType;
import kz.example.backend.virtualcollections.entities.Collection;
import kz.example.backend.virtualcollections.entities.User;
import kz.example.backend.virtualcollections.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    GET /users - получение всех пользователей
    GET /users/{id} - получение пользователя по ID
    POST /users - создание нового пользователя
    PUT /users/{id} - обновление пользователя
    DELETE /users/{id} - удаление пользователя
    GET /users/{id}/collections - коллекции пользователя
    GET /users/{id}/followers - подписчики пользователя
    GET /users/{id}/following - на кого подписан пользователь
    GET /users/{id}/achievements - достижения пользователя
*/

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(user, existingUser, "id");

        return ResponseEntity.ok(userService.createUser(existingUser));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}/collections")
    public List<Collection> getUserCollections(@PathVariable("id") Long id) {
        return userService.getUserCollections(id);
    }

    @GetMapping("/{id}/followers")
    public List<User> getUserFollowers(@PathVariable("id") Long id) {
        return userService.getUserFollowers(id);
    }

    @GetMapping("/{id}/following")
    public List<User> getUserFollowing(@PathVariable("id") Long id) {
        return userService.getUserFollowing(id);
    }

    @GetMapping("/{id}/achievements")
    public List<AchievementType> getUserAchievements(@PathVariable("id") Long id) {
        return userService.getUserAchievements(id);
    }
}
