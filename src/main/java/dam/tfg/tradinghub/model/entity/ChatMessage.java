package dam.tfg.tradinghub.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_messages")
public class ChatMessage extends BaseEntityModel {

    @Field("id_sender")
    @JsonProperty("id_sender")
    private String idSender;

    @Field
    @JsonProperty
    private String content;

    @Field("id_trueque")
    @JsonProperty("id_trueque")
    private String idTrueque;

    @Field
    @JsonProperty
    private Instant timestamp;
}