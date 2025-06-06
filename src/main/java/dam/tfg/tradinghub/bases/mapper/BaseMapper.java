package dam.tfg.tradinghub.bases.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;
import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;
import dam.tfg.tradinghub.exception.custom.mapping.InvalidObjectIdFormatException;

import java.util.List;

public interface    BaseMapper<E extends BaseEntityModel, D extends BaseDTOModel> {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    D entityToDto(E entity);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    E dtoToEntity(D dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    List<D> entityToDtoList(List<E> entityList);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    List<E> dtoToEntityList(List<D> dtoList);


    @Named("objectIdToString")
    static String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    @Named("stringToObjectId")
    static ObjectId stringToObjectId(String id) {
        ObjectId objectId = null;
        if (id != null) {
            if (ObjectId.isValid(id)) {
                objectId = new ObjectId(id);
            } else {
                throw new InvalidObjectIdFormatException(String.format("El Id %s no es un ObjectId válido.", id));
            }
        }
        return objectId;
    }
}
