package dam.tfg.tradinghub.bases.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTOModel implements Serializable {

    private static final long serialVersionUID = -2690322652558490161L;

    private String id;
}
