package dam.tfg.tradinghub.repository;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class GridFsImageRepository {

    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations gridFsOperations;

    public GridFsImageRepository(GridFsTemplate gridFsTemplate, GridFsOperations gridFsOperations) {
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsOperations = gridFsOperations;
    }

    // Crud operations

    public List<GridFSFile> findAll() {
        return gridFsTemplate.find(query(where("_id").exists(true))).into(new ArrayList<>());
    }

    public Optional<GridFSFile> findById(String id) {
        GridFSFile file = gridFsTemplate.findOne(query(where("_id").is(id)));
        return Optional.ofNullable(file);
    }

    public ObjectId save(MultipartFile file) throws IOException {
        return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
    }

    public void deleteById(String id) {
        gridFsTemplate.delete(query(where("_id").is(id)));
    }

    public GridFsResource getResource(GridFSFile file) {
        return gridFsOperations.getResource(file);
    }
}
