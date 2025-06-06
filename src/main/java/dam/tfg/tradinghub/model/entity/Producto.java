package dam.tfg.tradinghub.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Producto extends BaseEntityModel {

    @Field
    @JsonProperty
    private String nombre;

    @Field
    @JsonProperty
    private String descripcion;

    @Field
    @JsonProperty
    private String categoria;

    @Field
    @JsonProperty
    private List<String> fotos;

    @Field("id_vendedor")
    @JsonProperty("id_vendedor")
    private String idVendedor;

    @Field("no_disponible")
    @JsonProperty("no_disponible")
    private boolean noDisponible;
}