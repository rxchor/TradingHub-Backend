package dam.tfg.tradinghub.mapper;


import org.mapstruct.Mapper;

import dam.tfg.tradinghub.model.dto.PenalizacionDTO;
import dam.tfg.tradinghub.model.entity.Penalizacion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PenalizacionMapper {

    PenalizacionDTO entityToDto(Penalizacion entity);

    Penalizacion dtoToEntity(PenalizacionDTO dto);

    List<PenalizacionDTO> entityToDtoList(List<Penalizacion> entityList);

    List<Penalizacion> dtoToEntityList(List<PenalizacionDTO> dtoList);
}