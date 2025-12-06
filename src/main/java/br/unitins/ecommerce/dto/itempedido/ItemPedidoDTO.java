package br.unitins.ecommerce.dto.itempedido;

public record ItemPedidoDTO(
    Integer quantidade,
    Double preco,
    Long idRelogio
) {
    
}
