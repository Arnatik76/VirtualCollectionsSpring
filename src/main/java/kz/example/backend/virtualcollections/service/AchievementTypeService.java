package kz.example.backend.virtualcollections.service;

import kz.example.backend.virtualcollections.entity.AchievementType;
import kz.example.backend.virtualcollections.exception.ResourceNotFoundException;
import kz.example.backend.virtualcollections.repository.AchievementTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AchievementTypeService {

    private final AchievementTypeRepository achievementTypeRepository;

    public List<AchievementType> getAchievementTypes() {
        return achievementTypeRepository.getAllAchievementTypes()
                .orElseThrow(() -> new ResourceNotFoundException("Achievement types not found"));
    }
}
