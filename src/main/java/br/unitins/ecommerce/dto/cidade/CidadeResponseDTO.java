package br.unitins.ecommerce.dto.cidade;

import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.model.endereco.Cidade;

public record CidadeResponseDTO (Long id, String nome, EstadoResponseDTO estado) {
    
    public CidadeResponseDTO (Cidade cidade) {

        this(cidade.getId(), cidade.getNome(), new EstadoResponseDTO(cidade.getEstado()));
    }
}
