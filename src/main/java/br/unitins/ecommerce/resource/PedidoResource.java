package br.unitins.ecommerce.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.ecommerce.dto.pedido.PedidoDTO;
import br.unitins.ecommerce.dto.pedido.PedidoResponseDTO;
import br.unitins.ecommerce.service.pedido.PedidoService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {
    
    @Inject
    PedidoService pedidoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed({"User", "Admin"})
    public Response insert(PedidoDTO dto) {

        String login = jwt.getSubject();
        
        PedidoResponseDTO retorno = pedidoService.insert(dto, login);
        return Response.status(201).entity(retorno).build();
    }

    @GET
    @Path("/{login}")
  //  @RolesAllowed({"User", "Admin"})
    public Response findAll(@PathParam("login") String login) {
        
        return Response.ok(pedidoService.findByAll(login)).build();
    }
}
