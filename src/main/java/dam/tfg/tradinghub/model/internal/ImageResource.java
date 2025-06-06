package dam.tfg.tradinghub.model.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResource {
    private byte[] content;
    private String filename;
    private MediaType mediaType;
}
