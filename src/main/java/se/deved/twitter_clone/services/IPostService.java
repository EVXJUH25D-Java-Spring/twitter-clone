package se.deved.twitter_clone.services;

import se.deved.twitter_clone.dtos.CreatePostRequest;
import se.deved.twitter_clone.exceptions.CreatePostException;
import se.deved.twitter_clone.models.Post;
import se.deved.twitter_clone.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPostService {

    Post createPost(CreatePostRequest request, User user) throws CreatePostException;
    List<Post> getAllPosts(int page, int size);
    Optional<Post> getPostById(UUID postId);
    Optional<Post> getPostByIdWithComments(UUID postId);
    void deletePostById(UUID postId, User user);
}