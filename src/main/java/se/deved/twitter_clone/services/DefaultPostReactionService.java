package se.deved.twitter_clone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.deved.twitter_clone.dtos.ReactPostRequest;
import se.deved.twitter_clone.exceptions.*;
import se.deved.twitter_clone.models.PostReaction;
import se.deved.twitter_clone.models.User;
import se.deved.twitter_clone.repositories.IPostReactionRepository;
import se.deved.twitter_clone.repositories.IPostRepository;
import se.deved.twitter_clone.repositories.IUserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPostReactionService implements IPostReactionService {

    private final IPostReactionRepository postReactionRepository;
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;

    @Override
    public PostReaction reactPost(ReactPostRequest request, User user) throws ReactPostException {
        var post = postRepository.findById(request.getPostId())
                .orElseThrow(ReactPostNotFoundException::new);

        var existingReaction = postReactionRepository.findByUserAndPost(user, post);
        PostReaction reaction;
        if (existingReaction.isPresent()) {
            reaction = existingReaction.get();
            reaction.setLiked(request.isLiked());
            reaction = postReactionRepository.save(reaction);
            log.info("User '{}' updated reaction to post '{}'", user.getId(), post.getId());
        } else {
            reaction = new PostReaction(request.isLiked(), user, post);
            reaction = postReactionRepository.save(reaction);
            log.info("User '{}' reacted to post '{}'", user.getId(), post.getId());
        }

        return reaction;
    }
}
