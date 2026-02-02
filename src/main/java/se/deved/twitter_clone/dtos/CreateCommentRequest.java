package se.deved.twitter_clone.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCommentRequest {
    private String content;
    private UUID postId;
}
