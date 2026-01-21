package se.deved.twitter_clone.dtos;

import lombok.Getter;
import lombok.Setter;
import se.deved.twitter_clone.models.PostReaction;
import se.deved.twitter_clone.models.User;

import java.util.UUID;

@Getter
@Setter
public class PostReactionResponse {

    private final UUID id;
    private boolean liked;
    private UserResponse user;
    private UUID postId;

    public PostReactionResponse(UUID id, boolean liked, User user, UUID postId) {
        this.id = id;
        this.liked = liked;
        this.user = UserResponse.fromModel(user);
        this.postId = postId;
    }

    public static PostReactionResponse fromModel(PostReaction reaction) {
        return new PostReactionResponse(
                reaction.getId(),
                reaction.isLiked(),
                reaction.getUser(),
                reaction.getPost().getId()
        );
    }
}
