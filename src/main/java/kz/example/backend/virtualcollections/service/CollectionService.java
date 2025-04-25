package kz.example.backend.virtualcollections.service;

import kz.example.backend.virtualcollections.entity.*;
import kz.example.backend.virtualcollections.exception.ResourceAlreadyExistException;
import kz.example.backend.virtualcollections.exception.ResourceNotFoundException;
import kz.example.backend.virtualcollections.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionCollaboratorRepository collectionCollaboratorRepository;
    private final CollectionItemRepository collectionItemRepository;
    private final CollectionCommentRepository collectionCommentRepository;
    private final CollectionLikeRepository collectionLikeRepository;

    // Collection
    public Collection createCollection(Collection collection) {
        if (collectionRepository.existsById(collection.getId())) {
            throw new ResourceAlreadyExistException("Collection already exists");
        }
        return collectionRepository.save(collection);
    }

    public List<Collection> getAllCollections() {
        return collectionRepository.getAllCollections()
                .orElseThrow(() -> new ResourceNotFoundException("No collections found"));
    }

    public Collection getCollectionById(Long id) {
        return collectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Collection not found with ID: " + id));
    }

    public Collection updateCollection(Long id, Collection collection) {
        if (!collectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Collection not found with ID: " + id);
        }
        collection.setId(id);
        return collectionRepository.save(collection);
    }

    public void deleteCollection(Long id) {
        if (!collectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Collection not found with ID: " + id);
        }
        collectionRepository.deleteById(id);
    }

    // CollectionItem
    public CollectionItem addCollectionItem(CollectionItem item) {
        if (collectionItemRepository.existsById(item.getId())) {
            throw new ResourceAlreadyExistException("Item already exists");
        }
        return collectionItemRepository.save(item);
    }

    public List<CollectionItem> getCollectionItems(Long id) {
        return collectionItemRepository.getCollectionItemByCollectionId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No items found for this collection with ID: " + id));
    }

    public List<CollectionComment> getCollectionItemsByCollectionId(Long id) {
        return collectionCommentRepository.findCollectionItemsByCollectionId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comments found for this collection"));
    }

    // CollectionCollaborator
    public List<CollectionCollaborator> getCollectionCollaborators(Long id) {
        return collectionCollaboratorRepository.findCollectionCollaboratorByCollectionId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No collaborators found for collection with ID: " + id));
    }

    public CollectionCollaborator addCollectionCollaborator(CollectionCollaborator collaborator) {
        if (collectionCollaboratorRepository.existsById(collaborator.getId())) {
            throw new ResourceAlreadyExistException("Collaborator already exists");
        }
        return collectionCollaboratorRepository.save(collaborator);
    }

    // CollectionComment
    public CollectionComment addCollectionComment(CollectionComment comment) {
        if (collectionCommentRepository.existsById(comment.getId())) {
            throw new ResourceAlreadyExistException("Comment already exists");
        }
        return collectionCommentRepository.save(comment);
    }

    // CollectionLike
    public Long getCollectionLikesCount(Long id) {
        return collectionLikeRepository.countCollectionLikesByCollectionId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No likes found for collection with ID: " + id));
    }

    public List<CollectionLike> getCollectionLikes(Long id) {
        return collectionLikeRepository.findCollectionLikesByCollectionId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No likes found for collection with ID: " + id));
    }

    public CollectionLike addCollectionLike(CollectionLike like) {
        if (collectionLikeRepository.existsById(like.getId())) {
            throw new ResourceAlreadyExistException("Like already exists");
        }
        return collectionLikeRepository.save(like);
    }
}
