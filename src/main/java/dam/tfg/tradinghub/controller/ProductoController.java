package dam.tfg.tradinghub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dam.tfg.tradinghub.bases.controller.BaseController;
import dam.tfg.tradinghub.model.dto.ProductoDTO;
import dam.tfg.tradinghub.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController extends BaseController<ProductoDTO> {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        super(productoService);
        this.productoService = productoService;
    }

    @GetMapping("/vendedor/{idVendedor}")
    public List<ProductoDTO> findAllByIdVendedor(@PathVariable String idVendedor) {
        return productoService.findAllByIdVendedor(idVendedor);
    }

    @GetMapping("/disponibles")
    public List<ProductoDTO> findAllDisponibles() {
        return productoService.findAllDisponibles();
    }

    @PutMapping("/disponibilidad/{id}")
    public ProductoDTO cambiarDisponibilidad(@PathVariable String id, @RequestBody boolean disponible) {
        return productoService.cambiarDisponibilidad(id, disponible);
    }

}