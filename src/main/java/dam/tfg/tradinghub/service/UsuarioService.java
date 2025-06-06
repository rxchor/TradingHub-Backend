package dam.tfg.tradinghub.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dam.tfg.tradinghub.bases.service.BaseServiceImpl;
import dam.tfg.tradinghub.exception.custom.auth.IncorrectPasswordException;
import dam.tfg.tradinghub.exception.custom.auth.InvalidCredentialsException;
import dam.tfg.tradinghub.exception.custom.mapping.InvalidObjectIdFormatException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityNotFoundException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityStorageException;
import dam.tfg.tradinghub.mapper.PenalizacionMapper;
import dam.tfg.tradinghub.mapper.UsuarioMapper;
import dam.tfg.tradinghub.model.dto.*;
import dam.tfg.tradinghub.model.entity.Penalizacion;
import dam.tfg.tradinghub.model.entity.Usuario;
import dam.tfg.tradinghub.model.internal.EstadoTrueque;
import dam.tfg.tradinghub.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService extends BaseServiceImpl<Usuario, UsuarioDTO> {

    private static final String ENTITY_NOT_FOUND_MESSAGE = "Usuario no encontrado";

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final UsuarioMapper usuarioMapper;

    private final PenalizacionMapper penalizacionMapper;

    private final ProductoService productoService;

    private final ReporteService reporteService;

    private final TruequeService truequeService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          UsuarioMapper usuarioMapper,
                          PenalizacionMapper penalizacionMapper,
                          ProductoService productoService,
                          ReporteService reporteService,
                          TruequeService truequeService
    ) {
        super(usuarioRepository, Usuario.class, usuarioMapper);
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
        this.penalizacionMapper = penalizacionMapper;
        this.productoService = productoService;
        this.reporteService = reporteService;
        this.truequeService = truequeService;
    }

    public UsuarioDTO findByEmailAndPassword(String email, String rawPassword) {

        Usuario usuario = this.usuarioRepository.findByEmail(email).orElseThrow( () ->
                new InvalidCredentialsException("Credenciales de usuario inválidas.")
        );

        if (!passwordEncoder.matches(rawPassword, usuario.getPassword())) {
            throw new IncorrectPasswordException("Contraseña incorrecta");
        }

        return usuarioMapper.entityToDto(usuario);
    }

    @Override // Hay que hacer override del save, ya que es un caso especial donde un campo especifico se debe de tocar
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new EntityStorageException("Ya existe un usuario con ese email.");
        }

        usuarioDTO.setPassword(
                this.passwordEncoder.encode(usuarioDTO.getPassword())
        );

        return super.save(usuarioDTO);
    }

    @Override
    public List<UsuarioDTO> saveAll(List<UsuarioDTO> usuariosDTO) {
        usuariosDTO.forEach(user -> {
            if (usuarioRepository.existsByEmail(user.getEmail())) {
                throw new EntityStorageException("Ya existe un usuario con ese email.");
            }
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        });

        return super.saveAll(usuariosDTO);
    }

    public UsuarioDTO saveUserWithoutPassword(UsuarioByAdminDTO usuarioDTO) {
        UsuarioDTO existingUser = findById(usuarioDTO.getId());
        if (!existingUser.getEmail().equals(usuarioDTO.getEmail())
                && usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new EntityStorageException("Ya existe un usuario con ese email.");
        }

        UsuarioDTO udto = new UsuarioDTO();
        udto.setId(usuarioDTO.getId());
        udto.setUsername(usuarioDTO.getUsername());
        udto.setEmail(usuarioDTO.getEmail());
        udto.setNumeroTelefono(usuarioDTO.getNumeroTelefono());
        udto.setAdministrador(usuarioDTO.isAdministrador());

        Usuario usuario = usuarioMapper.dtoToEntity(udto);
        Usuario entity = usuarioRepository.findById(new ObjectId(usuarioDTO.getId())).orElseThrow(() ->
                new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE)
        );
        usuario.setPassword(entity.getPassword());
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.entityToDto(usuario);
    }

    public void cambiarPassword(CambiarPasswordDTO dto) {
        Usuario usuario = usuarioRepository.findById(new ObjectId(dto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE));

        if (!passwordEncoder.matches(dto.getPasswordActual(), usuario.getPassword())) {
            throw new IncorrectPasswordException("La contraseña actual no es correcta.");
        }

        usuario.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        usuarioRepository.save(usuario);
    }

    // Penalizaciones

    public UsuarioDTO aplicarPenalizacion(String idUsuario, PenalizacionDTO penalizacionDTO) {
        Usuario usuario = usuarioRepository.findById(new ObjectId(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuario con id \"%s\" no encontrado", idUsuario)));

        Penalizacion penalizacion = penalizacionMapper.dtoToEntity(penalizacionDTO);
        usuario.setPenalizacion(penalizacion);

        ReporteDTO reporte = reporteService.findById(penalizacionDTO.getIdReporte());
        String itemId = reporte.getIdItem();

        if ("producto".equalsIgnoreCase(reporte.getTipoItem())) {
            productoService.cambiarDisponibilidad(itemId, false);
        } else if ("trueque".equalsIgnoreCase(reporte.getTipoItem())) {
            TruequeDTO trueque = truequeService.findById(itemId);
            trueque.setEstado(EstadoTrueque.RECHAZADO);
            truequeService.update(trueque.getId(), trueque);
        }
        reporte.setResuelto(true);
        reporteService.save(reporte);

        return usuarioMapper.entityToDto(usuarioRepository.save(usuario));
    }

    public UsuarioDTO revocarPenalizacion(String idUsuario) {
        Usuario usuario = usuarioRepository.findById(new ObjectId(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE));

        usuario.setPenalizacion(null);
        return usuarioMapper.entityToDto(usuarioRepository.save(usuario));
    }

    @Override
    public void deleteById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new InvalidObjectIdFormatException(String.format(INVALID_OBJECT_ID_FORMAT_MESSAGE, id));
        }
        if (!repository.existsById(new ObjectId(id))) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, entityClass.getSimpleName(), id));
        }

        // Eliminar todas las relaciones del usuario antes de eliminarlo
        reporteService.deleteAllByIdUsuarioReportado(id);
        productoService.deleteAllByIdVendedor(id);
        truequeService.deleteByUsuario(id);
        usuarioRepository.deleteById(new ObjectId(id));
    }
}
