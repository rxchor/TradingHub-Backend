package dam.tfg.tradinghub.exception.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import dam.tfg.tradinghub.bases.model.exception.BaseAppException;
import dam.tfg.tradinghub.exception.custom.auth.IncorrectPasswordException;
import dam.tfg.tradinghub.exception.custom.auth.InvalidCredentialsException;
import dam.tfg.tradinghub.exception.custom.file.*;
import dam.tfg.tradinghub.exception.custom.mapping.InvalidObjectIdFormatException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityDeletionException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityNotFoundException;
import dam.tfg.tradinghub.exception.custom.persistence.EntityStorageException;
import dam.tfg.tradinghub.exception.custom.persistence.ImageReadingException;
import dam.tfg.tradinghub.exception.model.THubErrorResponse;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxUploadSize;

    @ExceptionHandler({
            IncorrectPasswordException.class,
            InvalidCredentialsException.class
    })
    public ResponseEntity<THubErrorResponse> handleAuthenticationExceptions(BaseAppException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<THubErrorResponse> handleNotFound(BaseAppException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({
            InvalidObjectIdFormatException.class,
            EmptyFileException.class,
            InvalidFileExtensionException.class,
            InvalidFileNameException.class,
            NoImagesProvidedException.class,
            UnsupportedFileFormatException.class
    })
    public ResponseEntity<THubErrorResponse> handleBadRequests(BaseAppException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({
            EntityStorageException.class,
            EntityDeletionException.class,
            ImageReadingException.class
    })
    public ResponseEntity<THubErrorResponse> handleInternalExceptions(BaseAppException e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // Excepciones no customizadas

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<THubErrorResponse> handlerValidationException(MethodArgumentNotValidException e) {
        List<String> mensajes = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return buildResponse(HttpStatus.BAD_REQUEST, mensajes);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<THubErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        String message = String.format("El tamaño del archivo excede el límite permitido (%s por archivo).", maxUploadSize);
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<THubErrorResponse> handleGeneralException(Exception e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // Métodos para construir la respuesta de error

    private ResponseEntity<THubErrorResponse> buildResponse(HttpStatus status, String message) {
        THubErrorResponse errorResponse = new THubErrorResponse();
        errorResponse.setStatus(status.value());
        errorResponse.setMessages(List.of(message));
        errorResponse.setTimestamp(Instant.now());
        return new ResponseEntity<>(errorResponse, status);
    }

    private ResponseEntity<THubErrorResponse> buildResponse(HttpStatus status, List<String> messages) {
        THubErrorResponse errorResponse = new THubErrorResponse();
        errorResponse.setStatus(status.value());
        errorResponse.setMessages(messages);
        errorResponse.setTimestamp(Instant.now());
        return new ResponseEntity<>(errorResponse, status);
    }
}
