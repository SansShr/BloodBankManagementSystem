package exception;



public class PatientException extends Exception {

    public PatientException(String message) {
        super(message);
    }

    public PatientException(String message, Throwable cause) {
        super(message, cause);
    }
}
