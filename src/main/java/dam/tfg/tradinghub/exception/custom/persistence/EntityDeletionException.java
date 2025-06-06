package dam.tfg.tradinghub.exception.custom.persistence;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;

public class EntityDeletionException extends BaseAppException {
    public EntityDeletionException(String message) {
        super(message);
    }

    public EntityDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
