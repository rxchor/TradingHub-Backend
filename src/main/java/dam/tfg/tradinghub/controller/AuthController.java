package dam.tfg.tradinghub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import dam.tfg.tradinghub.model.dto.LoginRequest;
import dam.tfg.tradinghub.model.dto.UsuarioDTO;
import dam.tfg.tradinghub.security.JwtUtil;
import dam.tfg.tradinghub.service.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UsuarioService usuarioService;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {

        UsuarioDTO usuario = usuarioService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        return autenticar(usuario);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UsuarioDTO usuario) {

        UsuarioDTO usuarioDTO = this.usuarioService.save(usuario);

        return autenticar(usuarioDTO);
    }

    // Lo que devolveria el Token
    private ResponseEntity<Map<String, String>> autenticar(UsuarioDTO usuario) {
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.getId());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user_id", usuario.getId()
        ));
    }
}
