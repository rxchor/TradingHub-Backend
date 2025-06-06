package dam.tfg.tradinghub.model.dto;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioByAdminDTO extends BaseDTOModel {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50)
    private String username;
    
    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    private String password;

    @Positive(message = "El número de teléfono debe ser positivo")
    private int numeroTelefono;

    private boolean administrador;
}