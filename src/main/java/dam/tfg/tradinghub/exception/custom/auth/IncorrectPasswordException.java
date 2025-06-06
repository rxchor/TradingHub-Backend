package dam.tfg.tradinghub.exception.custom.auth;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class IncorrectPasswordException extends BaseAppException {
    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
