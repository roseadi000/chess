package dataaccess;

public class InvalidAuthException extends Exception {
    public InvalidAuthException(String message) {
        super(message);
    }
    public InvalidAuthException(String message, Throwable ex) {
        super(message, ex);
    }

}
