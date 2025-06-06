package dam.tfg.tradinghub.service;

import com.mongodb.client.gridfs.model.GridFSFile;

import dam.tfg.tradinghub.exception.custom.file.*;
import dam.tfg.tradinghub.exception.custom.persistence.EntityStorageException;
import dam.tfg.tradinghub.exception.custom.persistence.ImageReadingException;
import dam.tfg.tradinghub.model.internal.ImageResource;
import dam.tfg.tradinghub.repository.GridFsImageRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MediaService {


    private static final Map<String, MediaType> MEDIA_TYPES = Map.ofEntries(
            Map.entry("jpg", MediaType.IMAGE_JPEG),
            Map.entry("jpeg", MediaType.IMAGE_JPEG),
            Map.entry("png", MediaType.IMAGE_PNG),
            Map.entry("gif", MediaType.IMAGE_GIF),
            Map.entry("webp", MediaType.valueOf("image/webp")),
            Map.entry("svg", MediaType.valueOf("image/svg+xml")),
            Map.entry("bmp", MediaType.valueOf("image/bmp")),
            Map.entry("tiff", MediaType.valueOf("image/tiff")),
            Map.entry("ico", MediaType.valueOf("image/x-icon")),
            Map.entry("jfif", MediaType.valueOf("image/jpeg")),
            Map.entry("heif", MediaType.valueOf("image/heif")),
            Map.entry("heic", MediaType.valueOf("image/heic")),
            Map.entry("avif", MediaType.valueOf("image/avif"))
    );

    private final GridFsImageRepository gridFsImageRepository;

    @Autowired
    public MediaService(GridFsImageRepository gridFsImageRepository) {
        this.gridFsImageRepository = gridFsImageRepository;
    }

    public ImageResource obtenerImagen(String idImagen) {
        ImageResource imageResource;

        try {
            GridFSFile file = gridFsImageRepository.findById(idImagen).orElse(null);

            if (file != null) {
                GridFsResource resource = gridFsImageRepository.getResource(file);
                imageResource = new ImageResource(resource.getInputStream().readAllBytes(), file.getFilename(), MediaType.valueOf(resource.getContentType()));
            } else {
                imageResource = defaultImage();
            }
        } catch (IOException e) {
            throw new ImageReadingException("Error al leer la imagen desde GridFS", e);
        }

        return imageResource;
    }

    public String guardarImagen(MultipartFile imagen) {
        try {
            if (imagen.isEmpty()) throw new EmptyFileException("Archivo vacío");

            String originalFilename = imagen.getOriginalFilename();
            String extension = getExtension(originalFilename).toLowerCase();

            if (!MEDIA_TYPES.containsKey(extension)) {
                throw new UnsupportedFileFormatException("Formato no soportado: " + extension);
            }

            ObjectId id = gridFsImageRepository.save(imagen);

            return id.toHexString();

        } catch (IOException e) {
            throw new EntityStorageException("Error al guardar imagen en GridFS", e);
        }
    }

    public List<String> guardarImagenes(List<MultipartFile> imagenes) {
        if (imagenes == null || imagenes.isEmpty()) {
            throw new NoImagesProvidedException("No se proporcionaron imágenes");
        }

        return imagenes.stream().map(this::guardarImagen).toList();
    }

    public void eliminarImagen(String id) {
        gridFsImageRepository.deleteById(id);
    }

    private ImageResource defaultImage() {
        try {
            ClassPathResource resource = new ClassPathResource("static/images/no-image-found.jpg");
            byte[] content = resource.getInputStream().readAllBytes();
            return new ImageResource(content, "no-image-found.jpg", MediaType.IMAGE_JPEG);
        } catch (IOException e) {
            throw new ImageReadingException("Error al leer imagen por defecto", e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new InvalidFileNameException("Nombre de archivo inválido");
        }
        int index = filename.lastIndexOf('.');
        if (index == -1 || index == filename.length() - 1) {
            throw new InvalidFileExtensionException("Extensión inválida en el archivo: " + filename);
        }
        return filename.substring(index + 1);
    }
}
