package se.deved.twitter_clone.services;

import se.deved.twitter_clone.dtos.CreateCommentRequest;
import se.deved.twitter_clone.exceptions.CreateCommentException;
import se.deved.twitter_clone.models.Comment;
import se.deved.twitter_clone.models.User;

public interface ICommentService {

    Comment createComment(CreateCommentRequest request, User user) throws CreateCommentException;

}
