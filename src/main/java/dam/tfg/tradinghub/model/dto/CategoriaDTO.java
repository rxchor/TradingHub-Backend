package dam.tfg.tradinghub.model.dto;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO extends BaseDTOModel {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max= 50, message ="La categoría no puede tener más de 50 caracteres")
    private String nombreCategoria;
}
