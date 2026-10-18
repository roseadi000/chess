package dataaccess;

public class InvalidColorException extends Exception {
    public InvalidColorException(String message) {
        super(message);
    }
    public InvalidColorException(String message, Throwable ex) {
        super(message, ex);
    }

}
