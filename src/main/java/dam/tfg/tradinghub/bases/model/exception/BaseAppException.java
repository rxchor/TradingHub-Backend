package dam.tfg.tradinghub.bases.model.exception;

public abstract class BaseAppException extends RuntimeException {

    protected BaseAppException(String message) {
        super(message);
    }

    protected BaseAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
