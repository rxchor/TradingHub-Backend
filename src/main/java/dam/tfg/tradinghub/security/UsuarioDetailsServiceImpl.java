package dam.tfg.tradinghub.security;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dam.tfg.tradinghub.exception.custom.persistence.EntityNotFoundException;
import dam.tfg.tradinghub.model.entity.Usuario;
import dam.tfg.tradinghub.repository.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // En mi caso uso el Id de usuario como username, que es un ObjectId, por ende unico
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findById(new ObjectId(id))
                .orElseThrow( () -> new EntityNotFoundException("No se pudo encontrar un usuario con id: "+id));

        return User.builder()
                .username(usuario.getId().toHexString())
                .password(usuario.getPassword())
                .roles(usuario.isAdministrador() ? "ADMIN" : "USER")
                .build();
    }
}
