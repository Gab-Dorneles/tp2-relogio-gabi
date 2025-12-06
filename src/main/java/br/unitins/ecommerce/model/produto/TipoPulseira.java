package br.unitins.ecommerce.model.produto;

public enum TipoPulseira {
    COURO(1, "Couro"),
    METAL(2, "Metal"),
    BORRACHA(3, "Borracha"),
    TECIDO(4, "Tecido"),
    CERAMICA(5, "Cerâmica"),
    OURO(6, "Ouro"),
    PRATA(7, "Prata"),
    TITANIO(8, "Titânio");

    private final Integer id;
    private final String descricao;

    TipoPulseira(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoPulseira valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (TipoPulseira tipoPulseira : TipoPulseira.values()) {
            if (id.equals(tipoPulseira.getId())) {
                return tipoPulseira;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}

