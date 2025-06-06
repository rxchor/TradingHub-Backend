package dam.tfg.tradinghub.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class THubErrorResponse {
    private int status;
    private List<String> messages;
    private Instant timestamp;
}
