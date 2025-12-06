package br.unitins.ecommerce.model.produto.tipoRelogio;

import br.unitins.ecommerce.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class TipoRelogio extends DefaultEntity {
    
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    public TipoRelogio() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
