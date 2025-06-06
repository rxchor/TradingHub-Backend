package dam.tfg.tradinghub.repository;

import java.util.List;

import dam.tfg.tradinghub.bases.repository.BaseRepository;
import dam.tfg.tradinghub.model.entity.Producto;

public interface ProductoRepository extends BaseRepository<Producto> {
    List<Producto> findAllByCategoria(String categoria);

    List<Producto> findAllByIdVendedor(String idVendedor);

    List<Producto> findAllByNoDisponibleFalse();

    long countByCategoria(String categoria);

    void deleteAllByIdVendedor(String idVendedor);
}
