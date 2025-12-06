package br.unitins.ecommerce.service.pedido;

import java.util.List;

import br.unitins.ecommerce.dto.pedido.PedidoDTO;
import br.unitins.ecommerce.dto.pedido.PedidoResponseDTO;

public interface PedidoService {

    public PedidoResponseDTO insert(PedidoDTO dto, String login);

    public PedidoResponseDTO findById(Long id);

    public List<PedidoResponseDTO> findByAll();

    public List<PedidoResponseDTO> findByAll(String login);
}
