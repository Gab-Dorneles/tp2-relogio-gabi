package br.unitins.ecommerce.dto.fabricante;

import br.unitins.ecommerce.model.produto.Fabricante;

import java.time.format.DateTimeFormatter;

public record FabricanteResponseDTO(Long id, String nome, String descricao, String anoFundacao, String localizacao) {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public FabricanteResponseDTO (Fabricante fabricante, DateTimeFormatter formatter) {

        this(fabricante.getId(), fabricante.getNome(), fabricante.getDescricao(), fabricante.getAnoFundacao().format(formatter), fabricante.getLocalizacao());
    }

    public FabricanteResponseDTO (Fabricante fabricante) {

        this(fabricante.getId(), fabricante.getNome(), fabricante.getDescricao(), fabricante.getAnoFundacao().format(formatter), fabricante.getLocalizacao());
    }
}
