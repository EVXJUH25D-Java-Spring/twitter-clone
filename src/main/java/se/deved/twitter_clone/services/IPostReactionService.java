package se.deved.twitter_clone.services;

import se.deved.twitter_clone.dtos.ReactPostRequest;
import se.deved.twitter_clone.exceptions.ReactPostException;
import se.deved.twitter_clone.models.PostReaction;
import se.deved.twitter_clone.models.User;

public interface IPostReactionService {
    PostReaction reactPost(ReactPostRequest request, User user) throws ReactPostException;
}
