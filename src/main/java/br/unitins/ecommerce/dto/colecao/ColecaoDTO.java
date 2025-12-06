package br.unitins.ecommerce.dto.colecao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ColecaoDTO(
    
    
    @NotBlank(message = "Campo nome não pode estar vazio")
    @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
    String nome,

    @NotBlank(message = "Campo descrição não pode estar vazio")
    @Size(max = 10000, message = "O campo descrição deve possuir no máximo 10 mil caracteres.")
    String descricao
) {
    
}
