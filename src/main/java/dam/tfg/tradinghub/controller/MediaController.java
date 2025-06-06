package dam.tfg.tradinghub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dam.tfg.tradinghub.model.internal.ImageResource;
import dam.tfg.tradinghub.service.MediaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/images")
public class MediaController {

    private static final String MESSAGES = "messages";

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        ImageResource imageResource = mediaService.obtenerImagen(id);
        return ResponseEntity.ok().contentType(imageResource.getMediaType()).body(imageResource.getContent());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        String foto = mediaService.guardarImagen(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(MESSAGES, List.of("Imagen subida correctamente"), "foto", foto));
    }


    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> uploadMultipleImages (@RequestParam("files") List<MultipartFile> files) {
        List<String> fotos = mediaService.guardarImagenes(files);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(MESSAGES, List.of("Imagenes subidas correctamente"), "fotos", fotos));
    }

    @DeleteMapping("/{imageName}")
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable String imageName) {
        mediaService.eliminarImagen(imageName);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(MESSAGES, List.of("Imagen borrada correctamente")));
    }
}