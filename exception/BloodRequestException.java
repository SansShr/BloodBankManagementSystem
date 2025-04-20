package exception;

public class BloodRequestException extends Exception {

    public BloodRequestException(String message) {
        super(message);
    }

    public BloodRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
