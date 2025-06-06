package dam.tfg.tradinghub.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor()
@Document(collection = "users")
public class Usuario extends BaseEntityModel {

    @Field
    @JsonProperty
    private String username;

    @Field
    @JsonProperty
    @Indexed(unique = true)
    private String email;

    @Field
    @JsonProperty
    private String password;

    @Field("num_tlfno")
    @JsonProperty("num_tlfno")
    private int numeroTelefono;

    @Field("administrador")
    @JsonProperty("administrador")
    private boolean administrador;

    @Field("penalizacion")
    @JsonProperty("penalizacion")
    private Penalizacion penalizacion;
}
