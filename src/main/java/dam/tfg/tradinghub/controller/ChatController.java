package dam.tfg.tradinghub.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dam.tfg.tradinghub.bases.controller.BaseController;
import dam.tfg.tradinghub.model.dto.ChatMessageDTO;
import dam.tfg.tradinghub.service.ChatService;

import java.util.List;

@Controller
@RequestMapping("/chat")
@Validated
public class ChatController extends BaseController<ChatMessageDTO> {

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService chatService ,SimpMessagingTemplate messagingTemplate) {
        super(chatService);
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/trueque/{id}")
    public void send(@DestinationVariable String id, @Valid ChatMessageDTO message) {
        ChatMessageDTO chatMessageDTO = chatService.save(message);
        messagingTemplate.convertAndSend("/topic/trueque/" + id, chatMessageDTO);
    }

    @GetMapping("/trueque/{id}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(@PathVariable String id) {
        return ResponseEntity.ok(chatService.getChatHistory(id));
    }

}