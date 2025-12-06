package br.unitins.ecommerce.dto.relogio;

import br.unitins.ecommerce.dto.colecao.ColecaoResponseDTO;
import br.unitins.ecommerce.dto.fabricante.FabricanteResponseDTO;
import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioResponseDTO;
import br.unitins.ecommerce.model.produto.Relogio;
import br.unitins.ecommerce.model.produto.TipoPulseira;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record RelogioResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        String nomeImagem,
        String anoLancamento,
        Boolean resistenciaAgua,
        Boolean temDiamante,
        TipoPulseira tipoPulseira,
        TipoRelogioResponseDTO tipoRelogio,
        List<ColecaoResponseDTO> colecaos,
        FabricanteResponseDTO fabricante
) {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelogioResponseDTO(Relogio relogio, DateTimeFormatter formatter) {
        this(
                relogio.getId(),
                relogio.getNome(),
                relogio.getDescricao(),
                relogio.getPreco(),
                relogio.getNomeImagem(),
                relogio.getAnoLancamento().format(formatter),
                relogio.getResistenciaAgua(),
                relogio.getTemDiamante(),
                relogio.getTipoPulseira(),
                new TipoRelogioResponseDTO(relogio.getTipoRelogio()),
                relogio.getColecaos().stream().map(ColecaoResponseDTO::new).toList(), new FabricanteResponseDTO(relogio.getFabricante()));
    }

    public RelogioResponseDTO(Relogio relogio) {

        this(relogio.getId(),
                relogio.getNome(),
                relogio.getDescricao(),
                relogio.getPreco(),
                relogio.getNomeImagem(),
                relogio.getAnoLancamento().format(formatter),
                relogio.getResistenciaAgua(),
                relogio.getTemDiamante(),
                relogio.getTipoPulseira(),
                new TipoRelogioResponseDTO(relogio.getTipoRelogio()),
                relogio.getColecaos().stream().map(ColecaoResponseDTO::new).toList(), new FabricanteResponseDTO(relogio.getFabricante()));
    }
}
