package dam.tfg.tradinghub.exception.custom.file;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class EmptyFileException extends BaseAppException {
    public EmptyFileException(String message) {
        super(message);
    }

    public EmptyFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
