package dam.tfg.tradinghub.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Penalizacion implements Serializable {

    @Serial
    private static final long serialVersionUID = 5358729084387014396L;

    @Field
    @JsonProperty
    private String motivo;

    @Field("id_admin")
    @JsonProperty("id_admin")
    private String idAdmin;

    @Field("id_reporte")
    @JsonProperty("id_reporte")
    private String idReporte;

    @Field("fecha_fin")
    @JsonProperty("fecha_fin")
    private Instant fechaFin;
}
