package se.deved.twitter_clone.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se.deved.twitter_clone.dtos.*;
import se.deved.twitter_clone.exceptions.DeletePostNotFoundException;
import se.deved.twitter_clone.exceptions.InvalidPostContentException;
import se.deved.twitter_clone.models.User;
import se.deved.twitter_clone.services.IPostService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    // @Slf4j gör detta
    // private Logger logger = LoggerFactory.getLogger(PostController.class);

    private final IPostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal User user
    ) {
        try {
            var post = postService.createPost(request, user);
            return ResponseEntity.created(URI.create("/post")).body(PostResponse.fromModel(post));
        } catch (InvalidPostContentException ignored) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Content must be between 5 and 200 characters"));
        } catch (Exception exception) {
            log.error("Error creating post", exception);
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }

    @GetMapping("/all")
    public List<PostResponse> getAllPosts(
            @RequestParam int size,
            @RequestParam int page
    ) {
        return postService
                .getAllPosts(page, size)
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
            @AuthenticationPrincipal User user
    ) {
        try {
            postService.deletePostById(postId, user);
            return ResponseEntity.noContent().build();
        } catch (DeletePostNotFoundException ignored) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post could not be found"));
        } catch (Exception exception) {
            log.error("Error deleting post", exception);
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }
}
