package se.deved.twitter_clone.dtos;

import lombok.Getter;
import lombok.Setter;
import se.deved.twitter_clone.models.Comment;
import se.deved.twitter_clone.models.Post;
import se.deved.twitter_clone.models.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PostWithCommentsResponse {
    private final UUID id;
    private String content;
    private Date createdAt;
    private UserResponse creator;
    private int likes;
    private int dislikes;
    private List<CommentResponse> comments;

    public PostWithCommentsResponse(
            UUID id,
            String content,
            Date createdAt,
            User creator,
            List<Comment> comments,
            int likes,
            int dislikes
    ) {
        this.id = id;
        this.content = content;
        this.creator = UserResponse.fromModel(creator);
        this.createdAt = createdAt;
        this.comments = comments.stream().map(CommentResponse::fromModel).toList();
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public static PostWithCommentsResponse fromModel(Post post) {
        int likes = 0;
        int dislikes = 0;
        for (var reaction : post.getReactions()) {
            if (reaction.isLiked())
                likes++;
            else dislikes++;
        }

        return new PostWithCommentsResponse(
                post.getId(),
                post.getContent(),
                post.getCreatedAt(),
                post.getCreator(),
                post.getComments(), likes, dislikes
        );
    }
}
