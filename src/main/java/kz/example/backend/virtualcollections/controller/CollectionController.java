package kz.example.backend.virtualcollections.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import kz.example.backend.virtualcollections.entity.*;
import kz.example.backend.virtualcollections.service.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@AllArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAllCollections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }

    @PostMapping
    @Operation(summary = "Создание новой коллекции",
            description = "Создает новую коллекцию на основе переданных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Коллекция успешно создана",
                    content = @Content(schema = @Schema(implementation = Collection.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    public ResponseEntity<Collection> createCollection(
            @RequestBody @Valid @Parameter(description = "Данные для создания коллекции",
                    required = true) Collection collection) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(collectionService.createCollection(collection));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable("id") Long id, @RequestBody Collection collection) {
        Collection existingCollection = collectionService.getCollectionById(id);
        BeanUtils.copyProperties(collection, existingCollection, "id");
        return ResponseEntity.ok(collectionService.createCollection(existingCollection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable("id") Long id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<CollectionItem>> getCollectionItems(@PathVariable("id") Long id) {
        return ResponseEntity.ok(collectionService.getCollectionItems(id));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CollectionItem> addCollectionItem(@PathVariable("id") Long id, @RequestBody CollectionItem item) {
        Collection collection = collectionService.getCollectionById(id);
        item.setCollection(collection);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(collectionService.addCollectionItem(item));
    }

    @GetMapping("/{id}/collaborators")
    public ResponseEntity<List<CollectionCollaborator>> getCollectionCollaborators(@PathVariable("id") Long id) {
        return ResponseEntity.ok(collectionService.getCollectionCollaborators(id));
    }

    @PostMapping("/{id}/collaborators")
    public ResponseEntity<CollectionCollaborator> addCollectionCollaborator(
            @PathVariable("id") Long id, @RequestBody CollectionCollaborator collaborator) {
        Collection collection = collectionService.getCollectionById(id);
        collaborator.setCollection(collection);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(collectionService.addCollectionCollaborator(collaborator));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CollectionComment>> getCollectionComments(@PathVariable("id") Long id) {
        return ResponseEntity.ok(collectionService.getCollectionItemsByCollectionId(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CollectionComment> addCollectionComment(
            @PathVariable("id") Long id, @RequestBody CollectionComment comment) {
        Collection collection = collectionService.getCollectionById(id);
        comment.setCollection(collection);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(collectionService.addCollectionComment(comment));
    }

    @GetMapping("/{id}/likes/count")
    public ResponseEntity<Long> getCollectionLikesCount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(collectionService.getCollectionLikesCount(id));
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<CollectionLike>> getCollectionLikes(@PathVariable("id") Long id) {
        return ResponseEntity.ok(collectionService.getCollectionLikes(id));
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<CollectionLike> addCollectionLike(
            @PathVariable("id") Long id, @RequestBody CollectionLike like) {
        Collection collection = collectionService.getCollectionById(id);
        like.setCollection(collection);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(collectionService.addCollectionLike(like));
    }
}