package se.deved.twitter_clone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.deved.twitter_clone.dtos.CreateCommentRequest;
import se.deved.twitter_clone.exceptions.*;
import se.deved.twitter_clone.models.Comment;
import se.deved.twitter_clone.models.User;
import se.deved.twitter_clone.repositories.ICommentRepository;
import se.deved.twitter_clone.repositories.IPostRepository;
import se.deved.twitter_clone.repositories.IUserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCommentService implements ICommentService {

    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;

    @Override
    public Comment createComment(CreateCommentRequest request, User user) throws CreateCommentException {
        var post = postRepository.findById(request.getPostId())
                .orElseThrow(CreateCommentPostNotFoundException::new);

        if (request.getContent().length() < 5) {
            throw new InvalidCommentContentException();
        }

        var comment = new Comment(request.getContent(), user, post);
        comment = commentRepository.save(comment);
        log.info("Created comment with id '{}' for post '{}'", comment.getId(), post.getId());

        return comment;
    }
}
