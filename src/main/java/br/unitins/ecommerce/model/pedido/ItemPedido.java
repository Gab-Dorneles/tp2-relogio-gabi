package br.unitins.ecommerce.model.pedido;

import br.unitins.ecommerce.model.DefaultEntity;
import br.unitins.ecommerce.model.produto.Relogio;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido extends DefaultEntity {
    
    private Integer quantidade;
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_relogio")
    private Relogio relogio;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Relogio getRelogio() {
        return relogio;
    }

    public void setRelogio(Relogio relogio) {
        this.relogio = relogio;
    }
}
