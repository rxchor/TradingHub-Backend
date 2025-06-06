package dam.tfg.tradinghub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dam.tfg.tradinghub.bases.controller.BaseController;
import dam.tfg.tradinghub.model.dto.CategoriaDTO;
import dam.tfg.tradinghub.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController extends BaseController<CategoriaDTO> {
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        super(categoriaService);
        this.categoriaService = categoriaService;
    }

    @GetMapping("/nombre/{nombre}")
    public List<CategoriaDTO> findByNombre(@PathVariable String nombre) {
        return categoriaService.findByNombre(nombre);
    }
}
