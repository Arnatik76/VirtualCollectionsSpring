package kz.example.backend.virtualcollections.controller;

import kz.example.backend.virtualcollections.entity.MediaItem;
import kz.example.backend.virtualcollections.service.MediaItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    GET /api/media - получение всех медиа +
    GET /api/media/{id} - получение медиа по ID +
    POST /api/media - загрузка нового медиа +
    PUT /api/media/{id} - обновление информации о медиа +
    DELETE /api/media/{id} - удаление медиа +
    GET /api/media/{id}/tags - теги медиа +
    POST /api/media/{id}/tags - добавление тега к медиа +
*/

@RestController
@AllArgsConstructor
@RequestMapping("/media")
public class MediaItemsController {

    private final MediaItemService mediaItemService;

    @GetMapping
    public List<MediaItem> getAllMediaItems() {
        return mediaItemService.getAllMediaItems();
    }

    @GetMapping("/{id}")
    public MediaItem getMediaItemById(Long id) {
        return mediaItemService.getMediaItemById(id);
    }

    @PostMapping
    public MediaItem createMediaItem(MediaItem mediaItem) {
        return mediaItemService.createMediaItem(mediaItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaItem> updateMediaItem(@PathVariable Long id, @RequestBody MediaItem mediaItem) {
        MediaItem existingMediaItem = mediaItemService.getMediaItemById(id);
        if (existingMediaItem == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(mediaItem, existingMediaItem, "id");

        return ResponseEntity.ok(mediaItemService.createMediaItem(existingMediaItem));
    }

    @DeleteMapping("/{id}")
    public void deleteMediaItem(@PathVariable Long id) {
        mediaItemService.deleteMediaItem(id);

    }

    @GetMapping("/{id}/tags")
    public List<String> getMediaItemTags(@PathVariable Long id) {
        return mediaItemService.getTagsByMediaItemId(id);
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity addTagToMediaItem(@PathVariable Long id, @RequestBody String tag) {
        mediaItemService.addTagToMediaItem(id, tag);
        return ResponseEntity.ok().build();
    }
}
