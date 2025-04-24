package kz.example.backend.virtualcollections.service;

import kz.example.backend.virtualcollections.entity.MediaItem;
import kz.example.backend.virtualcollections.exception.ResourceAlreadyExistException;
import kz.example.backend.virtualcollections.repository.MediaItemRepository;
import kz.example.backend.virtualcollections.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import kz.example.backend.virtualcollections.exception.ResourceNotFoundException;
import kz.example.backend.virtualcollections.exception.ResourceAlreadyExistException;


import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class MediaItemService {

    private final MediaItemRepository mediaItemRepository;
    private final TagRepository tagRepository;

    public List<MediaItem> getAllMediaItems() {
        return mediaItemRepository.getAllMediaItems()
                .orElseThrow(() -> new ResourceNotFoundException("No media items found"));
    }

    public MediaItem getMediaItemById(Long id) {
        return mediaItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media item with ID " + id + " not found"));
    }

    public MediaItem createMediaItem(MediaItem mediaItem) {
        if (mediaItemRepository.existsById(mediaItem.getId())) {
            throw new ResourceAlreadyExistException("Media item already exists");
        }
        return mediaItemRepository.save(mediaItem);
    }

    public void deleteMediaItem(@PathVariable Long id) {
        if (!mediaItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Media item with ID " + id + " not found");
        }
        mediaItemRepository.deleteById(id);
    }

    public List<String> getTagsByMediaItemId(@PathVariable Long id) {
        return tagRepository.findTagsByMediaItemId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No tags found for media item with ID " + id));
    }

    public void addTagToMediaItem(@PathVariable Long mediaId, @PathVariable String tag) {
        if (!mediaItemRepository.existsById(mediaId)) {
            throw new ResourceNotFoundException("Media item with ID " + mediaId + " not found");
        }
        mediaItemRepository.addTagToMediaItem(mediaId, tag);
    }
}

