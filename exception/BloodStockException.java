package exception;

public class BloodStockException extends Exception {

    public BloodStockException(String message) {
        super(message);
    }

    public BloodStockException(String message, Throwable cause) {
        super(message, cause);
    }
}