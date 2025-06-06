package dam.tfg.tradinghub.service;

import org.springframework.stereotype.Service;

import dam.tfg.tradinghub.bases.service.BaseServiceImpl;
import dam.tfg.tradinghub.exception.custom.persistence.EntityStorageException;
import dam.tfg.tradinghub.mapper.ReporteMapper;
import dam.tfg.tradinghub.model.dto.ReporteDTO;
import dam.tfg.tradinghub.model.entity.Reporte;
import dam.tfg.tradinghub.repository.ReporteRepository;

@Service
public class ReporteService extends BaseServiceImpl<Reporte, ReporteDTO> {

    private final ProductoService productoService;

    private final TruequeService truequeService;

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository,
                          ReporteMapper reporteMapper,
                          ProductoService productoService,
                          TruequeService truequeService
    ) {
        super(reporteRepository, Reporte.class, reporteMapper);
        this.productoService = productoService;
        this.truequeService = truequeService;
        this.reporteRepository = reporteRepository;
    }

    @Override
    public ReporteDTO save(ReporteDTO dto) {
        if (dto.getId() == null && reporteRepository.existsByIdItemAndIdUsuarioReportante(dto.getIdItem(), dto.getIdUsuarioReportante())) {
            throw new EntityStorageException(String.format("Ya has reportado este %s anteriormente. Por favor, evite repetir reportes.",
                    dto.getTipoItem()));
        }

        if (dto.getNombreItem() == null || dto.getNombreItem().isBlank()) {
            switch (dto.getTipoItem()) {
                case "producto":
                    dto.setNombreItem(productoService.findById(dto.getIdItem()).getNombre());
                    break;
                case "trueque":
                    dto.setNombreItem(truequeService.findById(dto.getIdItem()).getNombreTrueque());
                    break;
                default:
                    dto.setNombreItem("Desconocido");
            }
        }
        return super.save(dto);
    }

    public void deleteAllByIdUsuarioReportado(String idUsuarioReportado) {
        reporteRepository.deleteAllByIdUsuarioReportado(idUsuarioReportado);
    }

}
