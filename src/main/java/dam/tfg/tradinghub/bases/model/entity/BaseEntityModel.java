package dam.tfg.tradinghub.bases.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class BaseEntityModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 4825328279667192816L;

    @Id
    @Field
    @JsonProperty
    private ObjectId id;
}
