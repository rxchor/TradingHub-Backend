package dam.tfg.tradinghub.bases.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import dam.tfg.tradinghub.bases.mapper.BaseMapper;
import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;
import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;
import dam.tfg.tradinghub.exception.custom.mapping.InvalidObjectIdFormatException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityNotFoundException;

import java.util.List;

public abstract class BaseServiceImpl<E extends BaseEntityModel, D extends BaseDTOModel> implements BaseService<D> {

    protected static final String ENTITY_NOT_FOUND_MESSAGE = "No se ha podido encontrar un objeto \"%s\" con id %s";
    protected static final String INVALID_OBJECT_ID_FORMAT_MESSAGE = "El Id %s no es un ObjectId válido.";

    protected final MongoRepository<E, ObjectId> repository;
    protected final BaseMapper<E, D> mapper;
    protected final Class<E> entityClass;

    protected BaseServiceImpl(MongoRepository<E, ObjectId> repository, Class<E> entityClass, BaseMapper<E, D> mapper) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.mapper = mapper;
    }

    @Override
    public List<D> findAll() {
        return mapper.entityToDtoList(repository.findAll());
    }

    @Override
    public D findById(String id) {
        if (ObjectId.isValid(id)) {
            E entity = repository.findById(new ObjectId(id))
                    .orElseThrow( () ->
                            new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, entityClass.getSimpleName(), id))
                    );
            return mapper.entityToDto(entity);
        } else {
            throw new InvalidObjectIdFormatException(String.format(INVALID_OBJECT_ID_FORMAT_MESSAGE, id));
        }
    }

    @Override
    public D update(String id, D dto) {
        if (!ObjectId.isValid(id)) {
            throw new InvalidObjectIdFormatException(String.format(INVALID_OBJECT_ID_FORMAT_MESSAGE, id));
        }

        if (!repository.existsById(new ObjectId(id))) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, entityClass.getSimpleName(), id));
        }

        dto.setId(id);
        return this.save(dto);
    }

    @Override
    public D save(D dto) {
        if (dto.getId() != null && !ObjectId.isValid(dto.getId())) {
            throw new InvalidObjectIdFormatException(String.format(INVALID_OBJECT_ID_FORMAT_MESSAGE, dto.getId()));
        }

        E entity = mapper.dtoToEntity(dto);
        entity = repository.save(entity);
        return mapper.entityToDto(entity);
    }

    @Override
    public List<D> saveAll(List<D> dtos) {
        dtos.forEach(dto -> {
            if (dto.getId() != null && !ObjectId.isValid(dto.getId())) {
                throw new InvalidObjectIdFormatException(String.format(INVALID_OBJECT_ID_FORMAT_MESSAGE, dto.getId()));
            }
        });

        List<E> entities = mapper.dtoToEntityList(dtos);
        entities = repository.saveAll(entities);
        return mapper.entityToDtoList(entities);
    }

    @Override
    public void deleteById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new InvalidObjectIdFormatException(String.format(INVALID_OBJECT_ID_FORMAT_MESSAGE, id));
        }
        if (!repository.existsById(new ObjectId(id))) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, entityClass.getSimpleName(), id));
        }

        repository.deleteById(new ObjectId(id));
    }
}
