package dam.tfg.tradinghub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dam.tfg.tradinghub.bases.controller.BaseController;
import dam.tfg.tradinghub.bases.service.BaseService;
import dam.tfg.tradinghub.model.dto.ReporteDTO;

@RestController
@RequestMapping("/reportes")
public class ReporteController extends BaseController<ReporteDTO> {

    @Autowired
    public ReporteController(BaseService<ReporteDTO> service) {
        super(service);
    }
}
