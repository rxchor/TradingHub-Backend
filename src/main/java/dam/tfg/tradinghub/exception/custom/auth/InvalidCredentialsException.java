package dam.tfg.tradinghub.exception.custom.auth;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class InvalidCredentialsException extends BaseAppException {
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
