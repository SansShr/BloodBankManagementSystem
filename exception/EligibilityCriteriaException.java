package exception;

public class EligibilityCriteriaException extends Exception {
    public EligibilityCriteriaException(String message) {
        super(message);
    }

    public EligibilityCriteriaException(String message, Throwable cause) {
        super(message, cause);
    }
}
