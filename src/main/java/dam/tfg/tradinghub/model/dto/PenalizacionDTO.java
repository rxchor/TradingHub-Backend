package dam.tfg.tradinghub.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PenalizacionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3157063138635526671L;

    @NotBlank
    private String motivo;

    @NotBlank
    private String idAdmin;

    @NotBlank
    private String idReporte;

    private Instant fechaFin;
}
