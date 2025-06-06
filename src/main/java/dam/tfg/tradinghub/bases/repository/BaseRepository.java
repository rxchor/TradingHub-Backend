package dam.tfg.tradinghub.bases.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntityModel> extends MongoRepository<E, ObjectId> {

}
