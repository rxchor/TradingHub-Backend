package dam.tfg.tradinghub.repository;

import java.util.Optional;

import dam.tfg.tradinghub.bases.repository.BaseRepository;
import dam.tfg.tradinghub.model.entity.Usuario;

public interface UsuarioRepository extends BaseRepository<Usuario> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
