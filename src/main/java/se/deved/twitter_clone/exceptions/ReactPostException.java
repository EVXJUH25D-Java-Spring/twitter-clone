package se.deved.twitter_clone.exceptions;

public class ReactPostException extends RuntimeException {
    public ReactPostException() {
    }

    public ReactPostException(String message) {
        super(message);
    }

    public ReactPostException(String message, Throwable inner) {
        super(message, inner);
    }
}
