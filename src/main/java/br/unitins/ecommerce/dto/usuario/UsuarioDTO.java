package br.unitins.ecommerce.dto.usuario;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
    @NotBlank(message = "O campo nome não pode estar nulo")
    @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
    String nome,

    @NotBlank(message = "O campo CPF não pode estar nulo")
    @Size(min = 14, max = 14, message = "O campo CPF deve seguir o padrão xxx.xxx.xxx-xx, respeitando os sinais")
    String cpf,

    @NotBlank(message = "O campo email não pode estar nulo")
    @Size(max = 30, message = "O campo nome deve possuir no máximo 30 caracteres.")
    String email,

    @NotBlank(message = "O campo login não pode estar nulo")
    @Size(max = 30, message = "O campo nome deve possuir no máximo 30 caracteres.")
    String login,

    // @NotBlank(message = "O campo senha não pode estar nulo")
    @Size(min = 8, message = "A senha deve possuir no mínimo 8 caracteres")
    String senha,

    @NotNull(message = "O campo perfil não pode estar nulo")
    String perfil,

    @NotNull(message = "O campo telefones não pode estar nulo")
    List<String> telefones
) {
    
}
