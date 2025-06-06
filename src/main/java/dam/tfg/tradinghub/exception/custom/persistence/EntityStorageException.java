package dam.tfg.tradinghub.exception.custom.persistence;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class EntityStorageException extends BaseAppException {
    public EntityStorageException(String message) {
        super(message);
    }

    public EntityStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
