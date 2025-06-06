package dam.tfg.tradinghub.repository;

import java.util.List;

import dam.tfg.tradinghub.bases.repository.BaseRepository;
import dam.tfg.tradinghub.model.entity.ChatMessage;

public interface ChatRepository extends BaseRepository<ChatMessage> {
    List<ChatMessage> findAllByIdTrueque(String idTrueque);

    void deleteAllByIdTrueque(String idTrueque);
}
