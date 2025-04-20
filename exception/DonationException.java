package exception;

public class DonationException extends Exception {

    public DonationException(String message) {
        super(message);
    }

    public DonationException(String message, Throwable cause) {
        super(message, cause);
    }
}