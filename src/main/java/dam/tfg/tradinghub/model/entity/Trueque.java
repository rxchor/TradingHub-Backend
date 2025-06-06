package dam.tfg.tradinghub.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;
import dam.tfg.tradinghub.model.internal.EstadoTrueque;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trades")
public class Trueque extends BaseEntityModel {

    @Field("id_vendedor")
    @JsonProperty("id_vendedor")
    private String idVendedor;

    @Field("id_interesado")
    @JsonProperty("id_interesado")
    private String idInteresado;

    @Field("nombre_trueque")
    @JsonProperty("nombre_trueque")
    private String nombreTrueque;

    @Field("imagen_preview")
    @JsonProperty("imagen_preview")
    private String imagenPreview;

    @Field("ids_productos_vendedor")
    @JsonProperty("ids_productos_vendedor")
    private List<String> idsProductosVendedor;

    @Field("ids_productos_interesado")
    @JsonProperty("ids_productos_interesado")
    private List<String> idsProductosInteresado;

    @Field
    @JsonProperty
    private EstadoTrueque estado;

}
