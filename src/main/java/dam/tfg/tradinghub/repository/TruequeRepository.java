package dam.tfg.tradinghub.repository;

import java.util.List;

import dam.tfg.tradinghub.bases.repository.BaseRepository;
import dam.tfg.tradinghub.model.entity.Trueque;

public interface TruequeRepository extends BaseRepository<Trueque> {
    List<Trueque> findAllByIdVendedorOrIdInteresado(String idVendedor, String idInteresado);

    void deleteByIdVendedorOrIdInteresado(String idVendedor, String idInteresado);
}
