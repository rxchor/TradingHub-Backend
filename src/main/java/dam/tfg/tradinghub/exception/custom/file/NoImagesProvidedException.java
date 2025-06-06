package dam.tfg.tradinghub.exception.custom.file;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class NoImagesProvidedException extends BaseAppException {
    public NoImagesProvidedException(String message) {
        super(message);
    }

    public NoImagesProvidedException(String message, Throwable cause) {
        super(message, cause);
    }
}
