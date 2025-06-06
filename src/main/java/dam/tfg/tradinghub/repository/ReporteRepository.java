package dam.tfg.tradinghub.repository;

import dam.tfg.tradinghub.bases.repository.BaseRepository;
import dam.tfg.tradinghub.model.entity.Reporte;

public interface ReporteRepository extends BaseRepository<Reporte> {
    void deleteAllByIdUsuarioReportado(String idUsuarioReportado);

    boolean existsByIdItemAndIdUsuarioReportante(String idItem, String idUsuarioReportante);
}
