package br.unitins.ecommerce.repository;

import java.util.List;

import br.unitins.ecommerce.model.pedido.Pedido;
import br.unitins.ecommerce.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    
    public List<Pedido> findAll(String login) {
        return find("usuario.login = ?1", login).list();
    }
    
    public List<Pedido> findAll(Long idUsuario) {
        return find("usuario.id = ?1", idUsuario).list();
    }

    public List<Pedido> findByUsuarioWhereIsFinished (Usuario usuario) {

        if (usuario == null)
            return null;

        return find("FROM Pedido WHERE usuario = ?1 AND ifConcluida = true", usuario).list();
    }

    public Pedido findByUsuarioWhereIsNotFinished (Usuario usuario) {

        if (usuario == null)
            return null;

        return find("FROM Pedido WHERE usuario = ?1 AND ifConcluida = false", usuario).firstResult();
    }
}
