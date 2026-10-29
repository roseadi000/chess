package dataaccess;

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
    public InvalidInputException(String message, Throwable ex) {
        super(message, ex);
    }

}
