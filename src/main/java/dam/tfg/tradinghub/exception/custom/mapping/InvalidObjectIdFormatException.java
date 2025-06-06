package dam.tfg.tradinghub.exception.custom.mapping;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class InvalidObjectIdFormatException extends BaseAppException {
    public InvalidObjectIdFormatException(String message) {
        super(message);
    }

    public InvalidObjectIdFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
