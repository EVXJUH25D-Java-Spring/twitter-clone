package se.deved.twitter_clone.exceptions;

public class DeletePostException  extends RuntimeException {
    public DeletePostException() {}

    public DeletePostException(String message) {
        super(message);
    }

    public DeletePostException(String message, Throwable inner) {
        super(message, inner);
    }
}
