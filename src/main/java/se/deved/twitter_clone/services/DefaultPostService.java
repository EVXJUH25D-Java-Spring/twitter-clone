package se.deved.twitter_clone.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.deved.twitter_clone.dtos.CreatePostRequest;
import se.deved.twitter_clone.exceptions.*;
import se.deved.twitter_clone.models.Post;
import se.deved.twitter_clone.models.User;
import se.deved.twitter_clone.repositories.IPostRepository;
import se.deved.twitter_clone.repositories.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPostService implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    @Override
    public Post createPost(CreatePostRequest request, User user) throws CreatePostException {
        if (request.getContent().length() < 5 || request.getContent().length() > 200) {
            throw new InvalidPostContentException();
        }

        var post = new Post(request.getContent(), user);
        post = postRepository.save(post);
        log.info("Post with id '{}' created", post.getId());
        return post;
    }

    @Override
    public List<Post> getAllPosts(int page, int size) {
        if (size > 100) {
            size = 100;
        }

        if (size < 0) {
            size = 0;
        }

        if (page < 0) {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable).toList();
    }

    @Override
    public Optional<Post> getPostById(UUID postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Optional<Post> getPostByIdWithComments(UUID postId) {
        return postRepository.findByIdWithComments(postId);
    }

    @Override
    public void deletePostById(UUID postId, User user) {
        var post = postRepository.findById(postId)
                .orElseThrow(DeletePostNotFoundException::new);
        
        if (!post.getCreator().getId().equals(user.getId())) {
            throw new DeletePostNotFoundException();
        }
        
        postRepository.delete(post);
    }
}
