package exception;

public class EmergencyServiceException extends Exception {

    public EmergencyServiceException(String message) {
        super(message);
    }

    public EmergencyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
