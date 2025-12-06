package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.dto.colecao.ColecaoDTO;
import br.unitins.ecommerce.dto.colecao.ColecaoResponseDTO;
import br.unitins.ecommerce.service.colecao.ColecaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/colecaos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColecaoResource {

    @Inject
    ColecaoService colecaoService;

    private static final Logger LOG = Logger.getLogger(ColecaoResource.class);

    @GET
    public List<ColecaoResponseDTO> getAll() {
        LOG.infof("Buscando todos as coleções");
        LOG.debug("ERRO DE DEBUG.");
        return colecaoService.getAll();
    }

    @GET
    @Path("/paginado")
    public List<ColecaoResponseDTO> getAll(
                            @QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Buscando todos as coleções");
        LOG.debug("ERRO DE DEBUG.");

        try {
            return colecaoService.getAll(page, pageSize);
        } catch (Exception e) {

            LOG.error(e);

            return null;
        }
    }

    @GET
    @Path("/{id}")
    public ColecaoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.infof("Buscando coleções por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");
        return colecaoService.getById(id);
    }

    @POST
    public Response insert(ColecaoDTO colecaoDTO) {
        LOG.infof("Inserindo um coleções: %s", colecaoDTO.nome());

        ColecaoResponseDTO genero = colecaoService.insert(colecaoDTO);
        LOG.infof("Coleção (%d) criado com sucesso.", genero.id());
        return Response.status(Status.CREATED).entity(genero).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ColecaoDTO colecaoDTO) {

        colecaoService.update(id, colecaoDTO);
        LOG.infof("Coleção (%d) atualizado com sucesso.", id);
        return Response
                .status(Status.NO_CONTENT) // 204
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {
        try {
            colecaoService.delete(id);
            LOG.infof("Coleção excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar um coleção: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/search/{nome}")
    public List<ColecaoResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
                
        return colecaoService.getByNome(nome, page, pageSize);
    }

    @GET
    @Path("/count")
    public long count(){

        return colecaoService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome){

        return colecaoService.countByNome(nome);
    }
}
