package dam.tfg.tradinghub.exception.custom.persistence;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class EntityNotFoundException extends BaseAppException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
