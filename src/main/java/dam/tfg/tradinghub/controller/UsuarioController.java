package dam.tfg.tradinghub.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dam.tfg.tradinghub.bases.controller.BaseController;
import dam.tfg.tradinghub.model.dto.CambiarPasswordDTO;
import dam.tfg.tradinghub.model.dto.PenalizacionDTO;
import dam.tfg.tradinghub.model.dto.UsuarioByAdminDTO;
import dam.tfg.tradinghub.model.dto.UsuarioDTO;
import dam.tfg.tradinghub.service.UsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController<UsuarioDTO> {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        super(usuarioService);
        this.usuarioService = usuarioService;
    }

    @PutMapping("/no-password/{id}")
    public ResponseEntity<UsuarioDTO> saveUserWithoutPassword(@PathVariable String id,@RequestBody UsuarioByAdminDTO dto) {
        dto.setId(id);
        UsuarioDTO updated = usuarioService.saveUserWithoutPassword(dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<String> cambiarPassword(@PathVariable String id, @RequestBody @Valid CambiarPasswordDTO dto) {
        dto.setId(id);
        usuarioService.cambiarPassword(dto);
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }

    @PutMapping("/penalizar/{id}")
    public ResponseEntity<UsuarioDTO> aplicarPenalizacion(
            @PathVariable String id,
            @RequestBody PenalizacionDTO dto
    ) {
        UsuarioDTO actualizado = usuarioService.aplicarPenalizacion(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/penalizar/{id}")
    public ResponseEntity<UsuarioDTO> revocarPenalizacion(@PathVariable String id) {
        UsuarioDTO actualizado = usuarioService.revocarPenalizacion(id);
        return ResponseEntity.ok(actualizado);
    }

}