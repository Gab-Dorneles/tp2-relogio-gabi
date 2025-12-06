package br.unitins.ecommerce.service.colecao;

import java.util.List;
import java.util.Set;

import br.unitins.ecommerce.dto.colecao.ColecaoDTO;
import br.unitins.ecommerce.dto.colecao.ColecaoResponseDTO;
import br.unitins.ecommerce.model.produto.Colecao;
import br.unitins.ecommerce.repository.ColecaoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ColecaoImplService implements ColecaoService {

    @Inject
    Validator validator;

    @Inject
    ColecaoRepository colecaoRepository;

    private Sort sort = Sort.by("id").ascending();

    @Override
    public List<ColecaoResponseDTO> getAll() {
        
        return colecaoRepository.findAll(sort).stream().map(ColecaoResponseDTO::new).toList();
    }

    @Override
    public List<ColecaoResponseDTO> getAll(int page, int pageSize) {
        
        return colecaoRepository.findAll(sort).page(page, pageSize).stream().map(ColecaoResponseDTO::new).toList();
    }

    @Override
    public ColecaoResponseDTO getById(Long id) {
        
        Colecao colecao = colecaoRepository.findById(id);

        if (colecao == null)
            throw new NotFoundException("Não encontrado");

        return new ColecaoResponseDTO(colecao);
    }

    @Override
    @Transactional
    public ColecaoResponseDTO insert(@Valid ColecaoDTO colecaoDTO) throws ConstraintViolationException {
        
        validar(colecaoDTO);

        Colecao colecao = new Colecao();

        colecao.setNome(colecaoDTO.nome());
        colecao.setDescricao(colecaoDTO.descricao());

        colecaoRepository.persist(colecao);

        return new ColecaoResponseDTO(colecao);
    }

    @Override
    @Transactional
    public ColecaoResponseDTO update(Long id, @Valid ColecaoDTO colecaoDTO) throws ConstraintViolationException {
        
        validar(colecaoDTO);

        Colecao colecao = colecaoRepository.findById(id);

        if (colecao == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        colecao.setNome(colecaoDTO.nome());
        colecao.setDescricao(colecaoDTO.descricao());

        return new ColecaoResponseDTO(colecao);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Colecao colecao = colecaoRepository.findById(id);

        if (colecaoRepository.isPersistent(colecao))
            colecaoRepository.delete(colecao);

        else
            throw new NotFoundException("Nenhum genero encontrado");
    }

    @Override
    public List<ColecaoResponseDTO> getByNome(String nome, int page, int pageSize) {
        
        return colecaoRepository.findByNome(nome, sort).page(page, pageSize).stream().map(ColecaoResponseDTO::new).toList();
    }

    @Override
    public Long count() {

        return colecaoRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return colecaoRepository.findByNome(nome, sort).count();
    }
    
    private void validar(ColecaoDTO colecaoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<ColecaoDTO>> violations = validator.validate(colecaoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
