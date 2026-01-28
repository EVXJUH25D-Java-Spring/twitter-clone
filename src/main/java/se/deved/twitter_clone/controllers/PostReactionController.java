package se.deved.twitter_clone.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.deved.twitter_clone.dtos.ErrorResponse;
import se.deved.twitter_clone.dtos.PostReactionResponse;
import se.deved.twitter_clone.dtos.ReactPostRequest;
import se.deved.twitter_clone.exceptions.ReactPostAuthException;
import se.deved.twitter_clone.exceptions.ReactPostNotFoundException;
import se.deved.twitter_clone.repositories.IPostReactionRepository;
import se.deved.twitter_clone.services.IPostReactionService;

@RestController
@RequestMapping("/react-post")
@RequiredArgsConstructor
@Slf4j
public class PostReactionController {

    private final IPostReactionService postReactionService;

    @PutMapping
    public ResponseEntity<?> reactPost(@RequestBody ReactPostRequest request) {
        try {
            var reaction = postReactionService.reactPost(request);
            return ResponseEntity.ok(PostReactionResponse.fromModel(reaction));
        } catch (ReactPostAuthException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Wrong username or password"));
        } catch (ReactPostNotFoundException ignored) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post could not be found"));
        } catch (Exception exception) {
            log.error("Error reacting to post", exception);
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }
}
