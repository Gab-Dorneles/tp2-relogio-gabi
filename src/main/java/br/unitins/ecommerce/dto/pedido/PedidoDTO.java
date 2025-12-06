package br.unitins.ecommerce.dto.pedido;

import java.util.List;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.itempedido.ItemPedidoDTO;

public record PedidoDTO(
    EnderecoDTO enderecoDTO,
    Integer pagamento, // 1 = boleto, 2 = pix, 3 = cartão de crédito
    CartaoCreditoDTO cartaoCreditoDTO,
    List<ItemPedidoDTO> itens
) {
    
}
