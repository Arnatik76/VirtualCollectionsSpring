package kz.example.backend.virtualcollections.services;

import kz.example.backend.virtualcollections.entities.Collection;
import kz.example.backend.virtualcollections.entities.CollectionCollaborator;
import kz.example.backend.virtualcollections.entities.CollectionComment;
import kz.example.backend.virtualcollections.entities.CollectionItem;
import kz.example.backend.virtualcollections.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionCollaboratorRepository collectionCollaboratorRepository;
    private final CollectionItemRepository collectionItemRepository;
    private final CollectionCommentRepository collectionCommentRepository;
    private final CollectionLikeRepository collectionLikeRepository;

    public CollectionService(CollectionRepository collectionRepository,
                             CollectionCollaboratorRepository collectionCollaboratorRepository,
                             CollectionItemRepository collectionItemRepository,
                             CollectionCommentRepository collectionCommentRepository,
                             CollectionLikeRepository collectionLikeRepository) {
        this.collectionRepository = collectionRepository;
        this.collectionCollaboratorRepository = collectionCollaboratorRepository;
        this.collectionItemRepository = collectionItemRepository;
        this.collectionCommentRepository = collectionCommentRepository;
        this.collectionLikeRepository = collectionLikeRepository;
    }

    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    public Collection getCollectionById(Long id) {
        return collectionRepository.findById(id).orElse(null);
    }

    public Collection createCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public void deleteCollection(Long id) { collectionRepository.deleteById(id); }

    public List<CollectionItem> getCollectionItems(Long id) {
        return collectionItemRepository.findCollectionItemsByCollectionId(id);
    }

    public List<CollectionCollaborator> getCollectionCollaborators(Long id) {
        return collectionCollaboratorRepository.findCollectionCollaboratorByCollectionId(id);
    }

    public CollectionCollaborator addCollectionCollaborator(CollectionCollaborator collaborator) {
        return collectionCollaboratorRepository.save(collaborator);
    }

    public List<CollectionComment> getCollectionItemsByCollectionId(Long id) {
        return collectionCommentRepository.findCollectionItemsByCollectionId(id);
    }

    public CollectionComment addCollectionComment(CollectionComment comment) {
        return collectionCommentRepository.save(comment);
    }

    public Long getCollectionLikesCount(Long id) {
        return collectionLikeRepository.countCollectionLikesByCollectionId(id);
    }
}
