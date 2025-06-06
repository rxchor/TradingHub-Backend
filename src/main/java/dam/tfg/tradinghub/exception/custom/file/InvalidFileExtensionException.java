package dam.tfg.tradinghub.exception.custom.file;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class InvalidFileExtensionException extends BaseAppException {
    public InvalidFileExtensionException(String message) {
        super(message);
    }

    public InvalidFileExtensionException(String message, Throwable cause) {
      super(message, cause);
    }
}
