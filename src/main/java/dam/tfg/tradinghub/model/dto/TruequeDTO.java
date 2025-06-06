package dam.tfg.tradinghub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;
import dam.tfg.tradinghub.model.internal.EstadoTrueque;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TruequeDTO extends BaseDTOModel {

    @NotBlank
    private String idVendedor;

    @NotBlank
    private String idInteresado;

    @NotBlank(message = "Debe haber un nombre para el trueque")
    private String nombreTrueque;

    @NotBlank(message = "Debe haber una imagen para el trueque")
    private String imagenPreview;

    @NotEmpty(message = "Debe haber al menos un producto del vendedor")
    private List<@NotBlank String> idsProductosVendedor;

    @NotEmpty(message = "Debe haber al menos un producto del interesado")
    private List<@NotBlank String> idsProductosInteresado;

    @NotNull(message = "El estado del trueque no puede ser nulo")
    private EstadoTrueque estado;


}