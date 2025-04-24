package kz.example.backend.virtualcollections.controller;

import kz.example.backend.virtualcollections.entity.AchievementType;
import kz.example.backend.virtualcollections.entity.ContentType;
import kz.example.backend.virtualcollections.entity.Tag;
import kz.example.backend.virtualcollections.service.AchievementTypeService;
import kz.example.backend.virtualcollections.service.ContentTypeService;
import kz.example.backend.virtualcollections.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    GET /api/content-types - получение всех типов контента +
    GET /api/tags - получение всех тегов +
    GET /api/achievement-types - получение типов достижений +
    GET /api/search/collections - поиск по коллекциям
    GET /api/search/media - поиск по медиа
    GET /api/search/users - поиск пользователей
*/

@RestController
@AllArgsConstructor
public class ApiController {

    private final ContentTypeService contentTypeService;
    private final TagService tagService;
    private final AchievementTypeService achievementTypeService;

    @GetMapping("/content-types")
    public List<ContentType> getContentTypes() {
        return contentTypeService.getContentTypes();
    }

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/achievement-types")
    public List<AchievementType> getAchievementTypes() {
        return achievementTypeService.getAchievementTypes();
    }
}
