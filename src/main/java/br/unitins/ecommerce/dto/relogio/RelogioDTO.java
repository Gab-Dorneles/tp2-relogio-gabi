package br.unitins.ecommerce.dto.relogio;

import br.unitins.ecommerce.dto.fabricante.FabricanteDTO;
import br.unitins.ecommerce.model.produto.TipoPulseira;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record RelogioDTO(

        @NotBlank(message = "O campo nome não pode estar nulo")
        @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
        String nome,

        @NotBlank(message = "O campo descrição não pode estar nulo")
        @Size(max = 10000, message = "O campo descrição deve possuir no máximo 10 mil caracteres.")
        String descricao,

        @NotNull(message = "O campo preco não pode estar nulo")
        @DecimalMin(value = "0")
        Double preco,

        @NotNull(message = "O campo anoLancamento não pode estar nulo")
        LocalDate anoLancamento,

        @NotNull(message = "O campo Resistencia a água não pode estar nulo")
        Boolean resistenciaAgua,

        @NotNull(message = "O campo Tem Diamante não pode estar nulo")
        Boolean temDiamante,

        @NotNull(message = "O campo Tipo Pulseira não pode estar nulo")
        TipoPulseira tipoPulseira,

        @NotNull(message = "Campo Tipo Relogio não pode estar vazio")
        Long tipoRelogio,

        @NotNull(message = "Campo Coleção não pode estar vazio")
        List<Long> colecaos,

        @NotNull(message = "Campo Fabricante não pode estar vazio")
        Long fabricante
) {

}
