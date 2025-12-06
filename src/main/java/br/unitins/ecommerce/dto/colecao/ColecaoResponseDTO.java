package br.unitins.ecommerce.dto.colecao;

import br.unitins.ecommerce.model.produto.Colecao;

public record ColecaoResponseDTO(Long id, String nome, String descricao) {
    
    public ColecaoResponseDTO(Colecao colecao) {

        this(colecao.getId(), colecao.getNome(), colecao.getDescricao());
    }
}
