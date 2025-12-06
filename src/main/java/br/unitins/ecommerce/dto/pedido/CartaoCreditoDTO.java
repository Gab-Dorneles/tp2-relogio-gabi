package br.unitins.ecommerce.dto.pedido;

import java.time.LocalDate;

public record CartaoCreditoDTO(
    String numeroCartao,
    String nomeImpressoCartao,
    LocalDate dataValidade,
    String codigoSeguranca,
    String bandeiraCartao
) {
    
}
