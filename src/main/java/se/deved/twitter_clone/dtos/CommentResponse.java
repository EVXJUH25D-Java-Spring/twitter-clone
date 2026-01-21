package se.deved.twitter_clone.dtos;

import lombok.Getter;
import lombok.Setter;
import se.deved.twitter_clone.models.Comment;
import se.deved.twitter_clone.models.User;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CommentResponse {

    private final UUID id;
    private String content;
    private Date createdAt;
    private UserResponse creator;
    private UUID postId;

    public CommentResponse(UUID id, String content, Date createdAt, User user, UUID postId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.creator = UserResponse.fromModel(user);
        this.postId = postId;
    }

    public static CommentResponse fromModel(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getCreator(),
                comment.getPost().getId()
        );
    }
}
