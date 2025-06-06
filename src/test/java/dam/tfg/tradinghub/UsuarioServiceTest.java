package dam.tfg.tradinghub;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import dam.tfg.tradinghub.exception.custom.auth.IncorrectPasswordException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityNotFoundException;
import dam.tfg.tradinghub.mapper.PenalizacionMapper;
import dam.tfg.tradinghub.mapper.UsuarioMapper;
import dam.tfg.tradinghub.model.dto.CambiarPasswordDTO;
import dam.tfg.tradinghub.model.dto.PenalizacionDTO;
import dam.tfg.tradinghub.model.dto.UsuarioDTO;
import dam.tfg.tradinghub.model.entity.Usuario;
import dam.tfg.tradinghub.repository.UsuarioRepository;
import dam.tfg.tradinghub.service.UsuarioService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PenalizacionMapper penalizacionMapper;

    @InjectMocks
    private UsuarioService usuarioServiceWithMocks;

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
    void cambiarPasswordCorrecta() {
        CambiarPasswordDTO dto = new CambiarPasswordDTO();
        dto.setId(userId.toString());
        dto.setPasswordActual("actual");
        dto.setNuevaPassword("nueva");

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("actual", "hashedPassword")).thenReturn(true);
        when(passwordEncoder.encode("nueva")).thenReturn("newHashed");

        usuarioServiceWithMocks.cambiarPassword(dto);

        verify(usuarioRepository).save(usuario);
        assertEquals("newHashed", usuario.getPassword());
    }

    @Test
    void cambiarPasswordIncorrecta() {
        CambiarPasswordDTO dto = new CambiarPasswordDTO();
        dto.setId(userId.toString());
        dto.setPasswordActual("wrong");

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrong", "hashedPassword")).thenReturn(false);

        assertThrows(IncorrectPasswordException.class, () -> usuarioServiceWithMocks.cambiarPassword(dto));
    }


    @Test
    void revocarPenalizacionExistente() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.entityToDto(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioServiceWithMocks.revocarPenalizacion(userId.toString());
        assertNull(usuario.getPenalizacion());
        assertEquals(usuarioDTO, result);
    }

    @Test
    void aplicarPenalizacionUsuarioNoEncontrado() {
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());
        String userIdHexString = userId.toHexString();
        PenalizacionDTO penalizacionDTO = new PenalizacionDTO();
        assertThrows(EntityNotFoundException.class, () -> usuarioServiceWithMocks.aplicarPenalizacion(userIdHexString, penalizacionDTO));
    }

}
