package dataaccess;

public class FieldEmptyException extends Exception {
    public FieldEmptyException(String message) {
        super(message);
    }
    public FieldEmptyException(String message, Throwable ex) {
        super(message, ex);
    }
}
