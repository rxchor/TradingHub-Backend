package dam.tfg.tradinghub.bases.service;

import java.util.List;

import dam.tfg.tradinghub.bases.model.dto.BaseDTOModel;

public interface BaseService<D extends BaseDTOModel> {
    List<D> findAll();
    D findById(String id);
    D update(String id, D dto);
    D save(D dto);
    List<D> saveAll(List<D> dtos);
    void deleteById(String id);
}