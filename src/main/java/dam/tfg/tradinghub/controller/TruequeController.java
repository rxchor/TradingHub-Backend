package dam.tfg.tradinghub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dam.tfg.tradinghub.bases.controller.BaseController;
import dam.tfg.tradinghub.model.dto.TruequeDTO;
import dam.tfg.tradinghub.model.internal.EstadoTrueque;
import dam.tfg.tradinghub.service.TruequeService;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TruequeController extends BaseController<TruequeDTO> {

    private final TruequeService truequeService;

    @Autowired
    public TruequeController(TruequeService truequeService){
        super(truequeService);
        this.truequeService = truequeService;
    }

    @GetMapping("/usuario/{userId}")
    public List<TruequeDTO> getByUsuario(@PathVariable String userId) {
        return ((TruequeService) service).findByUsuario(userId);
    }

    @PutMapping("/{id}/aceptar")
    public TruequeDTO aceptar(@PathVariable String id) {
        return truequeService.actualizarEstado(id, EstadoTrueque.ACEPTADO);
    }

    @PutMapping("/{id}/rechazar")
    public TruequeDTO rechazar(@PathVariable String id) {
        return truequeService.actualizarEstado(id, EstadoTrueque.RECHAZADO);
    }


}
