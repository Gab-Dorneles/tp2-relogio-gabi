package br.unitins.ecommerce.dto.endereco;

import br.unitins.ecommerce.model.endereco.Endereco;

public record EnderecoResponseDTO(
    Long id,
    String logradouro,
    String bairro,
    String numero,
    String complemento,
    String cep,
    String cidade
) {

    public EnderecoResponseDTO (Endereco endereco) {

        this(endereco.getId(), endereco.getLogradouro(), endereco.getBairro(), endereco.getNumero(), endereco.getComplemento() == null ? null : endereco.getComplemento(), endereco.getCep(), endereco.getCidade().getNome() + " - " + endereco.getCidade().getEstado().getSigla());
    }    
}
