package br.unitins.ecommerce.service.usuario;

import java.util.List;
import java.util.Set;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.endereco.EnderecoResponseDTO;
import br.unitins.ecommerce.dto.usuario.SenhaDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.usuario.Perfil;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.CidadeRepository;
import br.unitins.ecommerce.repository.EnderecoRepository;
import br.unitins.ecommerce.repository.UsuarioRepository;
import br.unitins.ecommerce.service.HashService;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UsuarioImplService implements UsuarioService {

    @Inject
    Validator validator;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    HashService hashService;

    private Sort sort = Sort.by("id").ascending();

    private static final Logger LOG = Logger.getLogger(UsuarioImplService.class);

    @Override
    public List<UsuarioResponseDTO> getAll() {

        return usuarioRepository.findAll(sort).stream().map(usuario -> new UsuarioResponseDTO(usuario, null)).toList();
    }

    @Override
    public List<UsuarioResponseDTO> getAll(int page, int pageSize) {

        return usuarioRepository.findAll(sort).page(page, pageSize).stream()
                .map(usuario -> new UsuarioResponseDTO(usuario, null)).toList();
    }

    @Override
    public UsuarioResponseDTO getById(Long id) {

        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NotFoundException("Não encontrado");

        return new UsuarioResponseDTO(usuario, usuario.getPerfil().toString());
    }

    @Override
    @Transactional
    public UsuarioResponseDTO insert(@Valid UsuarioDTO usuarioDto) throws ConstraintViolationException {

        validar(usuarioDto);

        Usuario entity = new Usuario();

        entity.setNome(usuarioDto.nome());

        entity.setCpf(usuarioDto.cpf());

        entity.setEmail(usuarioDto.email());

        entity.setLogin(usuarioDto.login());

        entity.setSenha(hashService.getHashSenha(usuarioDto.senha()));

        entity.setPerfil(Perfil.valueOf(usuarioDto.perfil()));

        for (String telefone : usuarioDto.telefones()) {

            entity.getTelefones().add(new Telefone(telefone));
        }

        usuarioRepository.persist(entity);

        return new UsuarioResponseDTO(entity, null);
    }

    @Override
    @Transactional
    public Usuario update(Long id, @Valid UsuarioDTO usuarioDto) throws ConstraintViolationException {

        validar(usuarioDto);

        Usuario entity = usuarioRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setNome(usuarioDto.nome());

        entity.setCpf(usuarioDto.cpf());

        entity.setEmail(usuarioDto.email());

        entity.setLogin(usuarioDto.login());

        // entity.setSenha(hashService.getHashSenha(usuarioDto.senha()));

        entity.setPerfil(Perfil.valueOf(usuarioDto.perfil()));

        // for (Telefone telefone : entity.getTelefones()) {

        // entity.getTelefones().remove(telefone);
        // }

        entity.getTelefones().clear();

        for (String telefones : usuarioDto.telefones()) {

            entity.getTelefones().add(new Telefone(telefones));
        }

        return entity;
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Usuario usuario = usuarioRepository.findById(id);

        if (usuarioRepository.isPersistent(usuario))
            usuarioRepository.delete(usuario);

        else
            throw new NotFoundException("Nenhum usuario encontrado");
    }

    @Override
    @Transactional
    public UsuarioResponseDTO salvarImage(Long id, String nomeImagem) {

        Usuario entity = usuarioRepository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return new UsuarioResponseDTO(entity, entity.getPerfil().toString());
    }

    @Override
    public List<UsuarioResponseDTO> getByNome(String nome, int page, int pageSize) {

        return usuarioRepository.findByNome(nome, sort).page(page, pageSize).stream()
                .map(usuario -> new UsuarioResponseDTO(usuario, null)).toList();
    }

    @Override
    public Long count() {

        return usuarioRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return usuarioRepository.findByNome(nome, sort).count();
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {

        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha);

        return usuario;
    }

    @Override
    public EnderecoResponseDTO getEndereco(Long idUsuario) throws NullPointerException, NotFoundException {

        if (idUsuario == null)
            throw new NullPointerException("Id do Usuário está nulo");

        Usuario usuario = usuarioRepository.findById(idUsuario);

        if (usuario == null)
            throw new NotFoundException("Nenhum Usuário encontrado com este ID");

        if (usuario.getEndereco() != null)
            return new EnderecoResponseDTO(usuario.getEndereco());

        else
            throw new NotFoundException("Este Usuário ainda não possui endereço");
    }

    @Override
    @Transactional
    public Usuario insert(@Valid EnderecoDTO enderecoDTO, Long idUsuario) {

        Endereco endereco = new Endereco();

        endereco.setLogradouro(enderecoDTO.logradouro());

        endereco.setBairro(enderecoDTO.bairro());

        endereco.setNumero(enderecoDTO.numero());

        if (enderecoDTO.complemento() != null)
            endereco.setComplemento(enderecoDTO.complemento());

        endereco.setCep(enderecoDTO.cep());

        endereco.setCidade(cidadeRepository.findById(enderecoDTO.cidade()));

        enderecoRepository.persist(endereco);

        Usuario usuario = usuarioRepository.findById(idUsuario);

        usuario.setEndereco(endereco);

        return usuario;
    }

    @Override
    @Transactional
    public Usuario update(Long idusuario, @Valid EnderecoDTO enderecoDTO) {

        Usuario usuario = usuarioRepository.findById(idusuario);

        Endereco endereco = usuario.getEndereco();

        if (endereco == null)
            throw new NotFoundException("Usuário não possui endereço ainda");

        endereco.setLogradouro(enderecoDTO.logradouro());

        endereco.setBairro(enderecoDTO.bairro());

        endereco.setNumero(enderecoDTO.numero());

        if (enderecoDTO.complemento() != null)
            endereco.setComplemento(enderecoDTO.complemento());

        endereco.setCep(enderecoDTO.cep());

        endereco.setCidade(cidadeRepository.findById(enderecoDTO.cidade()));

        return usuario;
    }

    @Override
    public Boolean verificaSenhaAtual(Long idUsuario, String senhaAtual) {

        Usuario usuario = usuarioRepository.findById(idUsuario);

        senhaAtual = hashService.getHashSenha(senhaAtual);

        if (senhaAtual.equals(usuario.getSenha()))
            return true;

        else
            return false;
    }

    @Override
    @Transactional
    public Usuario update(SenhaDTO senhaDTO, Long idUsuario) throws BadRequestException {

        LOG.info(senhaDTO);

        Usuario usuario = usuarioRepository.findById(idUsuario);

        if (senhaDTO.novaSenha().equals(senhaDTO.confirmarNovaSenha())) {

            usuario.setSenha(hashService.getHashSenha(senhaDTO.novaSenha()));

            return usuario;
        }

        else {

            throw new BadRequestException("Os campos nova senha e confirmação da senha não correspondem");
        }
    }

    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
