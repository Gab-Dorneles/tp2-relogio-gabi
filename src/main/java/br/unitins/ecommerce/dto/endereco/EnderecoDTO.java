package br.unitins.ecommerce.dto.endereco;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
    @NotBlank(message = "O campo logradouro não pode estar vazio")
    String logradouro,

    @NotBlank(message = "O campo bairro não pode estar vazio")
    String bairro,

    @NotBlank(message = "O campo número não pode estar vazio")
    String numero,
    
    String complemento,

    @NotBlank(message = "O campo cep não pode estar vazio")
    String cep,
    
    Long cidade
) {
    
}
