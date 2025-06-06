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
@Document(collection = "reports")
public class Reporte extends BaseEntityModel {

    @Field
    @JsonProperty
    private String idUsuarioReportante;

    @Field
    @JsonProperty
    private String usernameReportante;

    @Field
    @JsonProperty
    private String idUsuarioReportado;

    @Field
    @JsonProperty
    private String usernameReportado;

    @Field
    @JsonProperty
    private String titulo;

    @Field
    @JsonProperty
    private String descripcion;

    @Field
    @JsonProperty
    private boolean resuelto;

    @Field
    @JsonProperty
    private String tipoItem;

    @Field
    @JsonProperty
    private String idItem;

    @Field
    @JsonProperty
    private String nombreItem;

    @Field
    @JsonProperty
    private Instant fechaCreacion;
}
