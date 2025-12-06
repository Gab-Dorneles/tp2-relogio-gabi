package br.unitins.ecommerce.dto.tipoRelogio;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record TipoRelogioDTO(

    @NotBlank(message = "Campo nome não pode estar vazio")
    @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
    String nome,

    @NotNull(message = "Campo ano de descrição não pode estar vazio")
    String descricao
) {
    
}
