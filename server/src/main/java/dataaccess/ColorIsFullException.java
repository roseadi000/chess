package dataaccess;

public class ColorIsFullException extends Exception {
    public ColorIsFullException(String message) {
        super(message);
    }
    public ColorIsFullException(String message, Throwable ex) {
        super(message, ex);
    }
}
