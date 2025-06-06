package dam.tfg.tradinghub.mapper;

import org.mapstruct.Mapper;

import dam.tfg.tradinghub.bases.mapper.BaseMapper;
import dam.tfg.tradinghub.model.dto.ProductoDTO;
import dam.tfg.tradinghub.model.entity.Producto;

@Mapper(componentModel = "spring") // Para q spring lo reconozca como bean, si no da error al hacer inyección de dependencias
public interface ProductoMapper extends BaseMapper<Producto, ProductoDTO> {
}
