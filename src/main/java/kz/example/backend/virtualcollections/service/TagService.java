package kz.example.backend.virtualcollections.service;

import kz.example.backend.virtualcollections.entity.Tag;
import kz.example.backend.virtualcollections.exception.ResourceNotFoundException;
import kz.example.backend.virtualcollections.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getTags() {
        return tagRepository.getAllTags()
                .orElseThrow(() -> new ResourceNotFoundException("Tags not found"));
    }
}
