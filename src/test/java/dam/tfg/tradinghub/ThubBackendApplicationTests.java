package dam.tfg.tradinghub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dam.tfg.tradinghub.model.entity.Usuario;
import dam.tfg.tradinghub.repository.UsuarioRepository;
import dam.tfg.tradinghub.service.ProductoService;
import dam.tfg.tradinghub.service.ReporteService;
import dam.tfg.tradinghub.service.UsuarioService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ThubBackendApplicationTests {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReporteService reporteService;

    @Test
    void contextLoads() {
        // Si el contexto se carga sin errores, ya pasa
    }

    @Test
    void usuarioServiceDisponible() {
        assertNotNull(usuarioService);
    }

    @Test
    void productoServiceDisponible() {
        assertNotNull(productoService);
    }

    @Test
    void reporteServiceDisponible() {
        assertNotNull(reporteService);
    }

    @Test
    void sePuedeGuardarYLeerUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("Test");
        usuario.setEmail("test@correo.com");

        Usuario guardado = usuarioRepository.save(usuario);
        Optional<Usuario> encontrado = usuarioRepository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Test", encontrado.get().getUsername());
    }
}
