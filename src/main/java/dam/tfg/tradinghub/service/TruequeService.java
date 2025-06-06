package dam.tfg.tradinghub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dam.tfg.tradinghub.bases.service.BaseServiceImpl;
import dam.tfg.tradinghub.mapper.TruequeMapper;
import dam.tfg.tradinghub.model.dto.ProductoDTO;
import dam.tfg.tradinghub.model.dto.TruequeDTO;
import dam.tfg.tradinghub.model.entity.Trueque;
import dam.tfg.tradinghub.model.internal.EstadoTrueque;
import dam.tfg.tradinghub.repository.TruequeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TruequeService extends BaseServiceImpl<Trueque, TruequeDTO> {

    private final TruequeRepository truequeRepository;

    private final ProductoService productoService;

    private final ChatService chatService;

    @Autowired
    public TruequeService(TruequeRepository truequeRepository, TruequeMapper truequeMapper, ProductoService productoService, ChatService chatService) {
        super(truequeRepository, Trueque.class, truequeMapper);
        this.truequeRepository = truequeRepository;
        this.productoService = productoService;
        this.chatService = chatService;
    }

    public List<TruequeDTO> findByUsuario(String userId) {
        List<Trueque> trueques = truequeRepository
                .findAllByIdVendedorOrIdInteresado(userId, userId);

        return mapper.entityToDtoList(trueques);
    }

    public TruequeDTO actualizarEstado(String id, EstadoTrueque nuevoEstado) {
        TruequeDTO trueque = findById(id);

        // Solo marcar productos como no disponibles si se acepta
        if (nuevoEstado == EstadoTrueque.ACEPTADO) {
            List<String> todosLosIds = new ArrayList<>();
            todosLosIds.addAll(trueque.getIdsProductosVendedor());
            todosLosIds.addAll(trueque.getIdsProductosInteresado());

            for (String prodId : todosLosIds) {
                ProductoDTO producto = productoService.findById(prodId);
                producto.setNoDisponible(true);
                productoService.save(producto); // actualiza el producto
            }
        }

        trueque.setEstado(nuevoEstado);
        return save(trueque);
    }

    public void deleteByUsuario(String idVendedor) {
        List<TruequeDTO> trueques = findByUsuario(idVendedor);
        for (TruequeDTO trueque : trueques) {
            chatService.deleteAllByIdTrueque(trueque.getId());
        }
        truequeRepository.deleteByIdVendedorOrIdInteresado(idVendedor, idVendedor);
    }

}
