package dam.tfg.tradinghub.service;

import org.springframework.stereotype.Service;

import dam.tfg.tradinghub.bases.service.BaseServiceImpl;
import dam.tfg.tradinghub.mapper.ProductoMapper;
import dam.tfg.tradinghub.model.dto.ProductoDTO;
import dam.tfg.tradinghub.model.entity.Producto;
import dam.tfg.tradinghub.repository.ProductoRepository;

import java.util.List;

@Service
public class ProductoService extends BaseServiceImpl<Producto, ProductoDTO> {

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;


    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        super(productoRepository, Producto.class, productoMapper);
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    public List<ProductoDTO> findAllByCategoria(String categoria) {
        return productoMapper.entityToDtoList(productoRepository.findAllByCategoria(categoria));
    }

    public List<ProductoDTO> findAllByIdVendedor(String idVendedor) {
        return productoMapper.entityToDtoList(productoRepository.findAllByIdVendedor(idVendedor));
    }

    public List<ProductoDTO> findAllDisponibles() {
        return productoMapper.entityToDtoList(productoRepository.findAllByNoDisponibleFalse());
    }

    public ProductoDTO cambiarDisponibilidad(String idProducto, boolean disponible) {
        ProductoDTO producto = findById(idProducto);
        producto.setNoDisponible(!disponible);
        return save(producto);
    }

    public long countByCategoria(String categoria) {
        return productoRepository.countByCategoria(categoria);
    }

    public void deleteAllByIdVendedor(String idVendedor) {
        productoRepository.deleteAllByIdVendedor(idVendedor);
    }

}
