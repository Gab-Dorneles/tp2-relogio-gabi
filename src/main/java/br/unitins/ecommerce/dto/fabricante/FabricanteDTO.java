package br.unitins.ecommerce.dto.fabricante;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record FabricanteDTO(

    @NotBlank(message = "Campo nome não pode estar vazio")
    @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
    String nome,

    @NotBlank(message = "Campo descrição não pode estar vazio")
    @Size(max = 10000, message = "O campo descrição deve possuir no máximo 10 mil' caracteres.")
    String descricao,

    @NotNull(message = "Campo ano de fundação não pode estar vazio")
    @PastOrPresent
    LocalDate anoFundacao,

    @NotNull(message = "Campo ano de localização não pode estar vazio")
    String localizacao
) {
    
}
