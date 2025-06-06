package dam.tfg.tradinghub.bases.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;
import dam.tfg.tradinghub.bases.service.BaseService;

import java.util.List;

@Validated
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public abstract class BaseController<D extends BaseDTOModel> {

    protected final BaseService<D> service;

    protected BaseController(BaseService<D> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<D>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<D> create(@Valid @RequestBody D dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<D>> createAll(@Valid @RequestBody List<D> dtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(dtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable String id, @Valid @RequestBody D dto) {
        service.deleteById(id);
        dto.setId(id);
        D updated = service.save(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }
}