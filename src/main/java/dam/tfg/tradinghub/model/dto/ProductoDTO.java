package dam.tfg.tradinghub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO extends BaseDTOModel {

    @NotBlank
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @NotBlank
    private String categoria;

    private List<@NotBlank String> fotos;

    @NotBlank
    private String idVendedor;

    private boolean noDisponible;
}