package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.produto.tipoRelogio.TipoRelogio;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoRelogioRepository implements PanacheRepository<TipoRelogio> {
    
        public PanacheQuery<TipoRelogio> findByNome(String nome, Sort sort){

        if (nome == null)
            return null;

        return find("UPPER(nome) LIKE ?1 ", sort, "%"+nome.toUpperCase()+"%");
    }
}
