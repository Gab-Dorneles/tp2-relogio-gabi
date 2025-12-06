package br.unitins.ecommerce.dto.usuario;

import java.util.List;
import java.util.Map;

import br.unitins.ecommerce.model.endereco.Cidade;
import br.unitins.ecommerce.model.usuario.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String login,
    String senha,
    String perfil,
    List<String> telefones
) {

    public UsuarioResponseDTO (Usuario usuario, String perfil) {

        this(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getLogin(), usuario.getSenha(), perfil != null? perfil : usuario.getPerfil().getLabel(), usuario.getTelefones().stream().map(telefone -> telefone.getNumero()).toList());
    }

    public static Map<String, Object> viewEndereco(String logradouro, String bairro, String numero, String complemento,
            String cep, Cidade cidade) {
        return null;
    }
}
