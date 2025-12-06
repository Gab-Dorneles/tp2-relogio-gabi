package br.unitins.ecommerce.resource;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.relogio.RelogioDTO;
import br.unitins.ecommerce.dto.relogio.RelogioResponseDTO;
import br.unitins.ecommerce.form.RelogioImageForm;
import br.unitins.ecommerce.service.file.FileService;
import br.unitins.ecommerce.service.relogio.RelogioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/relogios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelogioResource {
    
    @Inject
    RelogioService relogioService;

    @Inject
    FileService fileService;
    
    private static final Logger LOG = Logger.getLogger(RelogioResource.class);

    @GET
    public List<RelogioResponseDTO> getAll() {
        LOG.info("Buscando todos as relogios.");
        LOG.debug("ERRO DE DEBUG.");
        return relogioService.getAll();
    }

    @GET
    @Path("/paginado")
    public List<RelogioResponseDTO> getAll(
                            @QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Buscando todos os relogios");
        LOG.debug("ERRO DE DEBUG.");

        try {
            return relogioService.getAll(page, pageSize);
        } catch (Exception e) {

            LOG.error(e);

            return null;
        }
    }

    @GET
    @RolesAllowed("Admin")
    @Path("/{id}")
    public RelogioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando relogio por ID: " + id);
        LOG.debug("ERRO DE DEBUG.");
        return relogioService.getById(id);
    }

    @POST
    @RolesAllowed("Admin")
    public Response insert(RelogioDTO relogioDTO) {
        LOG.infof("Inserindo uma relogio: %s", relogioDTO.nome());

        RelogioResponseDTO relogio = relogioService.insert(relogioDTO);

        LOG.infof("Relogios (%d) criado com sucesso.", relogio.id());

        return Response.status(Status.CREATED).entity(relogio).build();
    }

    @PUT
    @RolesAllowed("Admin")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, RelogioDTO relogioDTO) {
        
        RelogioResponseDTO relogio = relogioService.update(id, relogioDTO);
        LOG.infof("Relogio (%d) atualizado com sucesso.", id);
        return Response
                .status(Status.CREATED)
                .entity(relogio) // 204
                .build();
    }

    @DELETE
    @RolesAllowed("Admin")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {

        try {
            relogioService.delete(id);
            LOG.infof("Relogios (%d) excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar Relogio: parâmetros inválidos.", e);
            throw e;
        } catch (NotFoundException e) {
            LOG.errorf("Relogio (%d) não encontrado.", id);
            throw e;
        }
    }

    @GET
    @Path("/image/download/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {

        LOG.info("Download do Relogio");

        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename="+nomeImagem);
        return response.build();
    }

    @PATCH
    // @RolesAllowed("Admin")
    @Path("/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm RelogioImageForm form) {
        
        LOG.info("Upload Imagem do Relogio");

        try {
            fileService.salvar(form.getId(), form.getNomeImagem(), form.getImagem());
            return Response.noContent().build();
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }
    }

    @GET
    // @RolesAllowed("Admin")
    @Path("/search/{nome}")
    public List<RelogioResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
                
        return relogioService.getByNome(nome, page, pageSize);
    }

    @GET
    // @RolesAllowed("Admin")
    @Path("/count")
    public long count(){

        return relogioService.count();
    }

    @GET
    // @RolesAllowed("Admin")
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome){

        return relogioService.countByNome(nome);
    }

    @GET
    // @RolesAllowed("Admin")
    @Produces("application/pdf")
    @Path("/relatorio/{filtro}")
    public Response gerarRelatorio(@PathParam("filtro") String filtro){
        LOG.info("Baixando relatório de relogios");
        LOG.debug("ERRO DE DEBUG.");

        byte[] pdf = relogioService.criarRelatorioRelogios(filtro);
        return Response.ok(pdf).header("Content-Disposition", "filename=attachment;relatorioRelogios.pdf").build();
    }

    @GET
    // @RolesAllowed("Admin")
    @Produces("application/pdf")
    @Path("/relatorio")
    public Response gerarRelatorio(){
        LOG.info("Baixando relatório de relogios");
        LOG.debug("ERRO DE DEBUG.");

        byte[] pdf = relogioService.criarRelatorioRelogios(null);
        return Response.ok(pdf).header("Content-Disposition", "filename=attachment;relatorioRelogios.pdf").build();
    }
}
