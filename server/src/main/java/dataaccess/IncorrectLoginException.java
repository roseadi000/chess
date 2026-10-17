package dataaccess;

public class IncorrectLoginException extends Exception {
    public IncorrectLoginException(String message) {
        super(message);
    }
    public IncorrectLoginException(String message, Throwable ex) {
        super(message, ex);
    }
}
