package se.deved.twitter_clone.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.deved.twitter_clone.dtos.CommentResponse;
import se.deved.twitter_clone.dtos.CreateCommentRequest;
import se.deved.twitter_clone.dtos.ErrorResponse;
import se.deved.twitter_clone.exceptions.CreateCommentAuthException;
import se.deved.twitter_clone.exceptions.CreateCommentPostNotFoundException;
import se.deved.twitter_clone.exceptions.InvalidCommentContentException;
import se.deved.twitter_clone.services.ICommentService;

import java.net.URI;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest request) {
        try {
            var comment = commentService.createComment(request);
            return ResponseEntity.created(URI.create("/comment"))
                    .body(CommentResponse.fromModel(comment));
        } catch (CreateCommentAuthException ignored) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Wrong username or password"));
        } catch (CreateCommentPostNotFoundException ignored) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post could not be found"));
        } catch (InvalidCommentContentException ignored) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Comment must be at least 6 characters"));
        } catch (Exception exception) {
            // TODO: Implement proper logging
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Unexpected error"));
        }
    }
}
