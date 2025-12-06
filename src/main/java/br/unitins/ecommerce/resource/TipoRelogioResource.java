package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioDTO;
import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioResponseDTO;
import br.unitins.ecommerce.service.tipoRelogio.TipoRelogioService;
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

@Path("/tipo-relogios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoRelogioResource {
    
    @Inject
    TipoRelogioService tipoRelogioService;

    private static final Logger LOG = Logger.getLogger(TipoRelogioResource.class);

    @GET
    public List<TipoRelogioResponseDTO> getAll() {
        LOG.infof("Buscando todos os tipos relogios");
        LOG.debug("ERRO DE DEBUG.");
        return tipoRelogioService.getAll();
    }

    @GET
    @Path("/paginado")
    public List<TipoRelogioResponseDTO> getAll(
                            @QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Buscando todos os tipos relogios");
        LOG.debug("ERRO DE DEBUG.");

        try {
            return tipoRelogioService.getAll(page, pageSize);
        } catch (Exception e) {

            LOG.error(e);

            return null;
        }
    }

    @GET
    @Path("/{id}")
    public TipoRelogioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.infof("Buscando tipos relogios por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");
        return tipoRelogioService.getById(id);
    }

    @POST
    public Response insert(TipoRelogioDTO tipoRelogioDTO) {

        LOG.infof("Inserindo um tipo relogio: %s", tipoRelogioDTO.nome());

        return Response
                .status(Status.CREATED) // 201
                .entity(tipoRelogioService.insert(tipoRelogioDTO))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TipoRelogioDTO tipoRelogioDTO) {
        
        tipoRelogioService.update(id, tipoRelogioDTO);
        LOG.infof("Tipo relogio (%d) atualizado com sucesso.", id);
        return Response
                .status(Status.NO_CONTENT) // 204
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {
        try {
            tipoRelogioService.delete(id);
            LOG.infof("Tipo relogio excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar um Tipo relogio: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/search/{nome}")
    public List<TipoRelogioResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
                
        return tipoRelogioService.getByNome(nome, page, pageSize);
    }

    @GET
    @Path("/count")
    public long count(){

        return tipoRelogioService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome){

        return tipoRelogioService.countByNome(nome);
    }
}
