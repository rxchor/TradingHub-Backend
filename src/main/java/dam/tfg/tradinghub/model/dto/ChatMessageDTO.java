package dam.tfg.tradinghub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO extends BaseDTOModel {
    @NotBlank(message = "El id del remitente no puede estar vacío")
    private String idSender;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String content;

    @NotBlank(message = "El id del trueque no puede estar vacío")
    private String idTrueque;

    @NotNull(message = "La fecha no puede estar vacía")
    private Instant timestamp;
}
