package kz.example.backend.virtualcollections.controller;

import kz.example.backend.virtualcollections.entity.*;
import kz.example.backend.virtualcollections.service.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    GET /api/collections - получение всех коллекций +
    GET /api/collections/{id} - получение коллекции по ID +
    POST /api/collections - создание новой коллекции +
    PUT /api/collections/{id} - обновление коллекции +
    DELETE /api/collections/{id} - удаление коллекции +
    GET /api/collections/{id}/items - получение элементов коллекции +
    POST /api/collections/{id}/items - добавление элемента в коллекцию +
    GET /api/collections/{id}/collaborators - получение соавторов +
    POST /api/collections/{id}/collaborators - добавление соавтора +
    GET /api/collections/{id}/comments - комментарии к коллекции +
    POST /api/collections/{id}/comments - добавление комментария +
    GET /api/collections/{id}/likes - количество лайков +
    POST /api/collections/{id}/likes - лайк коллекции +
*/

@RestController
@RequestMapping("/collections")
@AllArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    public List<Collection> getAllCollections() {
        return collectionService.getAllCollections();
    }

    @GetMapping("/{id}")
    public Collection getCollectionById(@PathVariable("id") Long id) {
        return collectionService.getCollectionById(id);
    }

    @PostMapping
    public Collection createCollection(@RequestBody Collection collection) {
        return collectionService.createCollection(collection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable("id") Long id, @RequestBody Collection collection) {
        Collection existingCollection = collectionService.getCollectionById(id);
        if (existingCollection == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(collection, existingCollection, "id");

        return ResponseEntity.ok(collectionService.createCollection(existingCollection));
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable("id") Long id) {
        collectionService.deleteCollection(id);
    }

    @GetMapping("/{id}/items")
    public List<CollectionItem> getCollectionItems(@PathVariable("id") Long id) {
        return collectionService.getCollectionItems(id);
    }

    @GetMapping("/{id}/collaborators")
    public List<CollectionCollaborator> getCollectionCollaborators(@PathVariable("id") Long id) {
        return collectionService.getCollectionCollaborators(id);
    }

    @PostMapping("/{id}/collaborators")
    public CollectionCollaborator addCollectionCollaborator(@PathVariable("id") Long id, @RequestBody CollectionCollaborator collaborator) {
        Collection collection = collectionService.getCollectionById(id);
        if (collection == null) {
            throw new RuntimeException("Collection with ID: " + id + " not found");
        }
        collaborator.setCollection(collection);
        return collectionService.addCollectionCollaborator(collaborator);
    }

    @GetMapping("/{id}/comments")
    public List<CollectionComment> getCollectionComments(@PathVariable("id") Long id) {
        return collectionService.getCollectionItemsByCollectionId(id);
    }

    @PostMapping("/{id}/comments")
    public CollectionComment addCollectionComment(@PathVariable("id") Long id, @RequestBody CollectionComment comment) {
        Collection collection = collectionService.getCollectionById(id);
        if (collection == null) {
            throw new RuntimeException("Collection with ID: " + id + " not found");
        }
        comment.setCollection(collection);
        return collectionService.addCollectionComment(comment);
    }

    @GetMapping("/{id}/likes/count")
    public Long getCollectionLikesCount(@PathVariable("id") Long id) {
        return collectionService.getCollectionLikesCount(id);
    }

    @GetMapping("/{id}/likes")
    public List<CollectionLike> getCollectionLikes(@PathVariable("id") Long id) {
        return collectionService.getCollectionLikes(id);
    }

    @PostMapping("/{id}/likes")
    public CollectionLike addCollectionLike(@PathVariable("id") Long id, @RequestBody CollectionLike like) {
        Collection collection = collectionService.getCollectionById(id);
        if (collection == null) {
            throw new RuntimeException("Collection with ID: " + id + " not found");
        }
        like.setCollection(collection);
        return collectionService.addCollectionLike(like);
    }
}
