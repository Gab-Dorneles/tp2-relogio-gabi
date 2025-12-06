package br.unitins.ecommerce.repository;

// import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.ecommerce.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public PanacheQuery<Usuario> findByNome(String nome, Sort sort){

        if (nome == null)
            return null;

        return find("UPPER(nome) LIKE ?1 ", sort, "%"+nome.toUpperCase()+"%");
    }

    public Usuario findByLoginAndSenha(String login, String senha){
        if (login == null || senha == null)
            return null;

        return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
    }

    public Usuario findByLogin(String login) {
        return find("login = ?1 ", login).firstResult();
    }
    
    // public List<Usuario> findByNome (String nomePessoaFisica) {

    //     if (nomePessoaFisica == null)
    //         return null;

    //     return find("FROM Usuario WHERE UNACCENT(UPPER(pessoaFisica.nome)) LIKE UNACCENT(?1)", "%" + nomePessoaFisica.toUpperCase() + "%").list();
    // }

    // public Usuario findByLoginAndSenha(String login, String senha) {

    //     if (login == null || senha == null)
    //         return null;

    //     return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
    // }

    // public Usuario findByLogin(String login) throws NullPointerException {

    //     if (login == null)
    //         return null;

    //     return find("login = ?1 ", login).firstResult();
    // }
}
