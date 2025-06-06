package dam.tfg.tradinghub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO extends BaseDTOModel {

    @NotBlank(message = "El id del usuario reportante no puede estar vacío")
    private String idUsuarioReportante;

    @NotBlank(message = "El nombre del usuario reportante no puede estar vacío")
    private String usernameReportante;

    @NotBlank(message = "El id del usuario reportado no puede estar vacío")
    private String idUsuarioReportado;

    @NotBlank(message = "El nombre del usuario reportado no puede estar vacío")
    private String usernameReportado;

    @NotBlank(message = "El nombre del reporte no puede estar vacío")
    private String titulo;

    @NotBlank(message = "La descripción del reporte no puede estar vacía")
    private String descripcion;

    @NotBlank(message = "El tipo de reporte no puede estar vacío")
    private String tipoItem;

    @NotBlank(message = "El id del item del reporte no puede estar vacío")
    private String idItem;
    private String nombreItem;

    private boolean resuelto;

    @NotNull(message = "No puede haber un reporte sin fecha de creación")
    private Instant fechaCreacion;

}
