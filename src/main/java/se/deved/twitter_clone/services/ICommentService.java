package se.deved.twitter_clone.services;

import se.deved.twitter_clone.dtos.CreateCommentRequest;
import se.deved.twitter_clone.exceptions.CreateCommentException;
import se.deved.twitter_clone.models.Comment;

public interface ICommentService {

    Comment createComment(CreateCommentRequest request) throws CreateCommentException;

}
