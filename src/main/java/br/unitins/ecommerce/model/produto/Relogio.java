package br.unitins.ecommerce.model.produto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.unitins.ecommerce.model.produto.tipoRelogio.TipoRelogio;
import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Relogio extends Produto {

    private String nomeImagem;
    
    @Column(nullable = false)
    private LocalDate anoLancamento;

    @Column(nullable = false)
    private TipoPulseira tipoPulseira;

    @Column(nullable = false)
    private Boolean resistenciaAgua;

    @Column(nullable = false)
    private Boolean temDiamante;

    @ManyToOne
    @JoinColumn(name = "id_tipo_relogio", nullable = false)
    private TipoRelogio tipoRelogio;

    @ManyToMany
    @JoinTable(name = "colecao_relogio",
                joinColumns = @JoinColumn(name = "id_relogio"),
                inverseJoinColumns = @JoinColumn(name = "id_colecao"))
    private List<Colecao> colecaos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id", nullable = false)
    private Fabricante fabricante;

    private String nomeImagem1;
    private String nomeImagem2;
    private String nomeImagem3;

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public LocalDate getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(LocalDate anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public TipoRelogio getTipoRelogio() {
        return tipoRelogio;
    }

    public void setTipoRelogio(TipoRelogio tipoRelogio) {
        this.tipoRelogio = tipoRelogio;
    }

    public List<Colecao> getColecaos() {
        return colecaos;
    }

    public void setColecaos(List<Colecao> colecaos) {
        this.colecaos = colecaos;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public String getNomeImagem1() {
        return nomeImagem1;
    }

    public void setNomeImagem1(String nomeImagem1) {
        this.nomeImagem1 = nomeImagem1;
    }

    public String getNomeImagem2() {
        return nomeImagem2;
    }

    public void setNomeImagem2(String nomeImagem2) {
        this.nomeImagem2 = nomeImagem2;
    }

    public String getNomeImagem3() {
        return nomeImagem3;
    }

    public void setNomeImagem3(String nomeImagem3) {
        this.nomeImagem3 = nomeImagem3;
    }

    public TipoPulseira getTipoPulseira() {
        return tipoPulseira;
    }

    public void setTipoPulseira(TipoPulseira tipoPulseira) {
        this.tipoPulseira = tipoPulseira;
    }

    public Boolean getResistenciaAgua() {
        return resistenciaAgua;
    }

    public void setResistenciaAgua(Boolean resistenciaAgua) {
        this.resistenciaAgua = resistenciaAgua;
    }

    public Boolean getTemDiamante() {
        return temDiamante;
    }

    public void setTemDiamante(Boolean temDiamante) {
        this.temDiamante = temDiamante;
    }
}
