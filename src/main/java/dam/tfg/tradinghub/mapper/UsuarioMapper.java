package dam.tfg.tradinghub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dam.tfg.tradinghub.bases.mapper.BaseMapper;
import dam.tfg.tradinghub.model.dto.UsuarioDTO;
import dam.tfg.tradinghub.model.entity.Usuario;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PenalizacionMapper.class}) // Para q spring lo reconozca como bean, si no da error al hacer inyección de dependencias
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

    @Override
    @Mapping(target = "id", source = "id", qualifiedByName = "objectIdToString")
    @Mapping(target = "password", ignore = true)
    UsuarioDTO entityToDto(Usuario entity);

    @Override
    @Mapping(target = "id", source = "id", qualifiedByName = "objectIdToString")
    @Mapping(target = "password", ignore = true)
    List<UsuarioDTO> entityToDtoList(List<Usuario> entityList);

}
