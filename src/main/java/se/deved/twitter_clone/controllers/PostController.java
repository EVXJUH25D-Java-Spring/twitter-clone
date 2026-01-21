package se.deved.twitter_clone.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.deved.twitter_clone.dtos.*;
import se.deved.twitter_clone.exceptions.DeletePostAuthException;
import se.deved.twitter_clone.exceptions.DeletePostNotFoundException;
import se.deved.twitter_clone.exceptions.InvalidPostContentException;
import se.deved.twitter_clone.exceptions.CreatePostAuthException;
import se.deved.twitter_clone.services.IPostService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) {
        try {
            var post = postService.createPost(request);
            return ResponseEntity.created(URI.create("/post")).body(PostResponse.fromModel(post));
        } catch (InvalidPostContentException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Content must be between 5 and 200 characters"));
        } catch (CreatePostAuthException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Wrong username or password"));
        } catch (Exception exception) {
            // TODO: Implement proper logging
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }

    // TODO: Implement paging
    @GetMapping("/all")
    public List<PostResponse> getAllPosts() {
        return postService
                .getAllPosts()
                .stream()
                .map(PostResponse::fromModel)
                .toList();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(
            @PathVariable UUID postId,
            @RequestParam(required = false) boolean includeComments
    ) {
        if (includeComments) {
            return postService.getPostByIdWithComments(postId)
                    .map(post -> ResponseEntity.ok(PostWithCommentsResponse.fromModel(post)))
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return postService.getPostById(postId)
                    .map(post -> ResponseEntity.ok(PostResponse.fromModel(post)))
                    .orElse(ResponseEntity.notFound().build());
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePostById(
            @PathVariable UUID postId,
            @RequestBody DeletePostRequest request
    ) {
        try {
            postService.deletePostById(postId, request.getUsername(), request.getPassword());
            return ResponseEntity.noContent().build();
        } catch (DeletePostNotFoundException ignored) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post could not be found"));
        } catch (DeletePostAuthException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Wrong username or password"));
        } catch (Exception exception) {
            // TODO: Implement proper logging
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }
}
