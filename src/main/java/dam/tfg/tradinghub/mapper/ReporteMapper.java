package dam.tfg.tradinghub.mapper;

import org.mapstruct.Mapper;

import dam.tfg.tradinghub.bases.mapper.BaseMapper;
import dam.tfg.tradinghub.model.dto.ReporteDTO;
import dam.tfg.tradinghub.model.entity.Reporte;

@Mapper(componentModel = "spring")
public interface ReporteMapper extends BaseMapper<Reporte, ReporteDTO> {
}
