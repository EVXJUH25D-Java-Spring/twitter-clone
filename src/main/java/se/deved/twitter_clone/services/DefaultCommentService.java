package se.deved.twitter_clone.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.deved.twitter_clone.dtos.CreateCommentRequest;
import se.deved.twitter_clone.exceptions.*;
import se.deved.twitter_clone.models.Comment;
import se.deved.twitter_clone.repositories.ICommentRepository;
import se.deved.twitter_clone.repositories.IPostRepository;
import se.deved.twitter_clone.repositories.IUserRepository;
import se.deved.twitter_clone.utilities.AuthUtil;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements ICommentService {

    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;

    @Override
    public Comment createComment(CreateCommentRequest request) throws CreateCommentException {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(CreateCommentAuthException::new);

        if (!AuthUtil.validatePassword(user, request.getPassword())) {
            throw new CreateCommentAuthException();
        }

        var post = postRepository.findById(request.getPostId())
                .orElseThrow(CreateCommentPostNotFoundException::new);

        if (request.getContent().length() < 5) {
            throw new InvalidCommentContentException();
        }

        var comment = new Comment(request.getContent(), user, post);
        comment = commentRepository.save(comment);
        System.out.println("Created comment with id '" + comment.getId() + "' for post '" + post.getId() + "'");

        return comment;
    }
}
