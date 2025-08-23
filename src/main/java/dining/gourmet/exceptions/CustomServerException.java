package dining.gourmet.exceptions;

public class CustomServerException extends RuntimeException {
    public CustomServerException(String message) {
        super(message);
    }
}
