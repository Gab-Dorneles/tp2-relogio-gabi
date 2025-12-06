package br.unitins.ecommerce.resource;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.endereco.EnderecoResponseDTO;
import br.unitins.ecommerce.dto.usuario.SenhaDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.form.UserImageForm;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import br.unitins.ecommerce.service.file.UserFileService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
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

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    @Inject
    UsuarioService usuarioService;
    
    @Inject
    UserFileService fileService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    public List<UsuarioResponseDTO> getAll() {

        LOG.info("Buscando todos os usuários");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getAll();
    }

    @GET
    @Path("/paginado")
    public List<UsuarioResponseDTO> getAll(
                            @QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Buscando todos os usuarios");
        LOG.debug("ERRO DE DEBUG.");

        try {
            return usuarioService.getAll(page, pageSize);
        } catch (Exception e) {

            LOG.error(e);

            return null;
        }
    }

    @GET
    @Path("/{id}")
    public UsuarioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando usuário por id");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getById(id);
    }

    @GET
    @Path("/endereco/{id}")
    public EnderecoResponseDTO getEndereco(@PathParam("id") Long id) throws NullPointerException, NotFoundException {
        LOG.info("Buscando endereço do usuário");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getEndereco(id);
    }

    @GET
    @Path("/verifica/{senha}/{id}")
    public Boolean verificaSenhaAtual(@PathParam("id") Long id, @PathParam("senha") String senhaAtual) throws NullPointerException, NotFoundException {
        LOG.info("Verificando se a senha inserida pelo usuário corresponde a cadastrada no banco");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.verificaSenhaAtual(id, senhaAtual);
    }

    @POST
    public Response insert(UsuarioDTO usuarioDto) {

        LOG.infof("Usuário criado com sucesso.");

        return Response
                .status(Status.CREATED) // 201
                .entity(usuarioService.insert(usuarioDto))
                .build();
    }

    @PATCH
    @Path("/update/senha/{id}")
    public Response update(@PathParam("id") Long id, SenhaDTO senhaDTO) throws BadRequestException {

        try {
            
            LOG.infof("Senha atualizada com sucesso.");

            Usuario usuario = usuarioService.update(senhaDTO, id);

            return Response
                    .status(Status.CREATED) // 201
                    .entity(usuario)
                    .build();
        } catch (BadRequestException e) {
            
            return null;
        }
    }

    @PATCH
    @Path("/endereco/insert/{id}")
    public Response insert(@PathParam("id") Long id, EnderecoDTO enderecoDTO) {

        LOG.infof("Endereço criado com sucesso.");

        Usuario usuario = usuarioService.insert(enderecoDTO, id);

        return Response
                .status(Status.CREATED) // 201
                .entity(usuario)
                .build();
    }

    @PATCH
    @Path("/endereco/update/{id}")
    public Response update(@PathParam("id") Long id, EnderecoDTO enderecoDTO) {

        Usuario usuario = usuarioService.update(id, enderecoDTO);
        LOG.infof("Endereço do usuário (%d) atualizado com sucesso.", id);

        return Response
                .status(Status.CREATED) // 204
                .entity(usuario)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UsuarioDTO usuarioDto) {
    
        Usuario usuario = usuarioService.update(id, usuarioDto);
        LOG.infof("Usuário (%d) atualizado com sucesso.", id);
        // LOG.info(usuario.getLogin());

        return Response
                .status(Status.CREATED) // 204
                .entity(usuario)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {
        try {
            usuarioService.delete(id);
            LOG.infof("Produto excluído com sucesso.", id);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar usuário: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/image/download/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {

        LOG.info("pego a imagem");

        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename="+nomeImagem);
        return response.build();
    }

    @PATCH
    // @RolesAllowed("Admin")
    @Path("/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm UserImageForm form) {
        
        LOG.info("chegou aqui?");

        try {
            Usuario usuario = fileService.salvar(form.getId(), form.getNomeImagem(), form.getImagem());
            return Response.status(Status.CREATED).entity(usuario).build();
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }
    }

    @GET
    @Path("/search/{nome}")
    public List<UsuarioResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
                
        return usuarioService.getByNome(nome, page, pageSize);
    }

    @GET
    @Path("/count")
    public long count(){

        return usuarioService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome){

        return usuarioService.countByNome(nome);
    }
}
