package dam.tfg.tradinghub.mapper;

import org.mapstruct.Mapper;

import dam.tfg.tradinghub.bases.mapper.BaseMapper;
import dam.tfg.tradinghub.model.dto.CategoriaDTO;
import dam.tfg.tradinghub.model.entity.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper extends BaseMapper<Categoria, CategoriaDTO> {
}
