package dam.tfg.tradinghub.service;

import org.springframework.stereotype.Service;

import dam.tfg.tradinghub.bases.service.BaseServiceImpl;
import dam.tfg.tradinghub.mapper.ChatMessageMapper;
import dam.tfg.tradinghub.model.dto.ChatMessageDTO;
import dam.tfg.tradinghub.model.entity.ChatMessage;
import dam.tfg.tradinghub.repository.ChatRepository;

import java.util.List;

@Service
public class ChatService extends BaseServiceImpl<ChatMessage, ChatMessageDTO> {

    private final ChatRepository chatRepository;

    private final ChatMessageMapper chatMessageMapper;

    public ChatService(ChatRepository chatRepository, ChatMessageMapper chatMessageMapper) {
        super(chatRepository, ChatMessage.class, chatMessageMapper);
        this.chatRepository = chatRepository;
        this.chatMessageMapper = chatMessageMapper;
    }

    public List<ChatMessageDTO> getChatHistory(String id) {
        return chatMessageMapper.entityToDtoList(chatRepository.findAllByIdTrueque(id));
    }

    public void deleteAllByIdTrueque(String id) {
        chatRepository.deleteAllByIdTrueque(id);
    }
}
