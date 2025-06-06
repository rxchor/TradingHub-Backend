package dam.tfg.tradinghub.exception.custom.file;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class InvalidFileNameException extends BaseAppException {
    public InvalidFileNameException(String message) {
        super(message);
    }

    public InvalidFileNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
