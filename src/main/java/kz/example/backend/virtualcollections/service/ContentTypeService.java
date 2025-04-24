package kz.example.backend.virtualcollections.service;

import kz.example.backend.virtualcollections.exception.ResourceNotFoundException;
import kz.example.backend.virtualcollections.repository.ContentTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import kz.example.backend.virtualcollections.entity.ContentType;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    public List<ContentType> getContentTypes() {
        return contentTypeRepository.getAllContentTypes()
                .orElseThrow(() -> new ResourceNotFoundException("Content types not found"));
    }

}
