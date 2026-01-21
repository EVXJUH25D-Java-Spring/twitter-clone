package se.deved.twitter_clone.exceptions;

public class CreateCommentException extends RuntimeException {
    public CreateCommentException() {}

    public CreateCommentException(String message) {
        super(message);
    }

    public CreateCommentException(String message, Throwable inner) {
        super(message, inner);
    }
}
