package se.deved.twitter_clone.services;

import se.deved.twitter_clone.dtos.ReactPostRequest;
import se.deved.twitter_clone.exceptions.ReactPostException;
import se.deved.twitter_clone.models.PostReaction;

public interface IPostReactionService {
    PostReaction reactPost(ReactPostRequest request) throws ReactPostException;
}
