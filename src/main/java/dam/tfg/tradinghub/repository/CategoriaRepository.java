package dam.tfg.tradinghub.repository;

import java.util.List;

import dam.tfg.tradinghub.bases.repository.BaseRepository;
import dam.tfg.tradinghub.model.entity.Categoria;

public interface CategoriaRepository extends BaseRepository<Categoria> {
    List<Categoria> findByNombreCategoria(String nombreCategoria);
}
