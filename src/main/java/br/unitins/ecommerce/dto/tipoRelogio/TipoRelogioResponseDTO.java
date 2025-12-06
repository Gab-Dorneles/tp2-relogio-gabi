package br.unitins.ecommerce.dto.tipoRelogio;

import br.unitins.ecommerce.model.produto.tipoRelogio.TipoRelogio;

public record TipoRelogioResponseDTO(Long id, String nome, String descricao) {

    public TipoRelogioResponseDTO(TipoRelogio tipoRelogio) {
        this(tipoRelogio.getId(), tipoRelogio.getNome(), tipoRelogio.getDescricao());
    }

}
