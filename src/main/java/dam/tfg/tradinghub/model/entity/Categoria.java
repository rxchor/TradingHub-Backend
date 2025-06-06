package dam.tfg.tradinghub.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import dam.tfg.tradinghub.bases.model.entity.BaseEntityModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categorias")
public class Categoria extends BaseEntityModel {

    @Field("nombre_categoria")
    @JsonProperty("nombre_categoria")
    private String nombreCategoria;
}
