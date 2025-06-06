package dam.tfg.tradinghub.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dam.tfg.tradinghub.bases.service.BaseServiceImpl;
import dam.tfg.tradinghub.exception.custom.mapping.InvalidObjectIdFormatException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityDeletionException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityNotFoundException;
import dam.tfg.tradinghub.mapper.CategoriaMapper;
import dam.tfg.tradinghub.model.dto.CategoriaDTO;
import dam.tfg.tradinghub.model.dto.ProductoDTO;
import dam.tfg.tradinghub.model.entity.Categoria;
import dam.tfg.tradinghub.repository.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService extends BaseServiceImpl<Categoria, CategoriaDTO> {

    private static final String DELETE_CATEGORY_ERROR_MESSAGE = "No se puede eliminar la categoría %s porque tiene productos asociados.";

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final ProductoService productoService;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper, ProductoService productoService) {
        super(categoriaRepository, Categoria.class, categoriaMapper);
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.productoService = productoService;
    }

    public List<CategoriaDTO> findByNombre(String nombreCategoria) {
        return categoriaMapper.entityToDtoList(categoriaRepository.findByNombreCategoria(nombreCategoria));
    }

    @Override
    public CategoriaDTO update(String id, CategoriaDTO dto) {
        if (!ObjectId.isValid(id)) {
            throw new InvalidObjectIdFormatException(String.format(INVALID_OBJECT_ID_FORMAT_MESSAGE, id));
        }
        if (!categoriaRepository.existsById(new ObjectId(id))) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, Categoria.class.getSimpleName(), id));
        }

        CategoriaDTO existingCategoria = findById(id);
        List<ProductoDTO> productos = productoService.findAllByCategoria(existingCategoria.getNombreCategoria());
        if (!productos.isEmpty()) {
            productos = productos.stream().map(producto -> {
                producto.setCategoria(dto.getNombreCategoria());
                return producto;
            }).toList();
            productoService.saveAll(productos);
        }
        dto.setId(id);
        return this.save(dto);
    }

    @Override
    public void deleteById(String id) {
        CategoriaDTO categoria = findById(id);
        if (productoService.countByCategoria(categoria.getNombreCategoria()) > 0) {
            throw new EntityDeletionException(String.format(DELETE_CATEGORY_ERROR_MESSAGE, categoria.getNombreCategoria(), id));
        }
        super.deleteById(id);
    }
}