package dam.tfg.tradinghub.exception.custom.file;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class UnsupportedFileFormatException extends BaseAppException {
    public UnsupportedFileFormatException(String message) {
        super(message);
    }

    public UnsupportedFileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
