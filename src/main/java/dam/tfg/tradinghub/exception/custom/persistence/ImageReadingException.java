package dam.tfg.tradinghub.exception.custom.persistence;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class ImageReadingException extends BaseAppException {
    public ImageReadingException(String message) {
        super(message);
    }

    public ImageReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
