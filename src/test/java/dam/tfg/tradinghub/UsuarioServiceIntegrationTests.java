package dam.tfg.tradinghub;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dam.tfg.tradinghub.model.dto.UsuarioDTO;
import dam.tfg.tradinghub.model.entity.Usuario;
import dam.tfg.tradinghub.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UsuarioServiceIntegrationTests {

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuario;
    private ObjectId userId;

    @BeforeEach
    void setUp() {
        userId = new ObjectId();
        usuario = new Usuario();
        usuario.setId(userId);
        usuario.setPassword("hashedPassword");
    }

    @Test
    void comprobarUsuariosDTOTienenPasswordNull() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(userId.toHexString());
        usuarioDTO.setUsername("testUser");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setNumeroTelefono(123456789);
        usuarioDTO.setAdministrador(false);
        usuarioDTO.setPassword("testPassword");

        usuarioDTO = usuarioService.save(usuarioDTO);

        assertNull(usuarioDTO.getPassword());
    }
}
