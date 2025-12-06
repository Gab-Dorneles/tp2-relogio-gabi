package br.unitins.ecommerce.service.cidade;

import java.util.List;
import java.util.Set;

import br.unitins.ecommerce.dto.cidade.CidadeDTO;
import br.unitins.ecommerce.dto.cidade.CidadeResponseDTO;
import br.unitins.ecommerce.model.endereco.Cidade;
import br.unitins.ecommerce.repository.CidadeRepository;
import br.unitins.ecommerce.repository.EstadoRepository;
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
public class CidadeImplService implements CidadeService {

    @Inject
    Validator validator;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EstadoRepository estadoRepository;

    private Sort sort = Sort.by("id").ascending();

    @Override
    public List<CidadeResponseDTO> getAll() {
        
        return cidadeRepository.findAll(sort).stream().map(CidadeResponseDTO::new).toList();
    }

    @Override
    public List<CidadeResponseDTO> getAll(int page, int pageSize) {
        
        return cidadeRepository.findAll(sort).page(page, pageSize).stream().map(CidadeResponseDTO::new).toList();
    }

    @Override
    public CidadeResponseDTO getById(Long id) {
        
        Cidade cidade = cidadeRepository.findById(id);

        if (cidade == null)
            throw new NotFoundException("Não encontrado");

        return new CidadeResponseDTO(cidade);
    }

    @Override
    @Transactional
    public CidadeResponseDTO insert(@Valid CidadeDTO cidadeDTO) throws ConstraintViolationException {
        
        validar(cidadeDTO);

        Cidade cidade = new Cidade();

        cidade.setNome(cidadeDTO.nome());

        cidade.setEstado(estadoRepository.findById(cidadeDTO.estado()));

        cidadeRepository.persist(cidade);

        return new CidadeResponseDTO(cidade);
    }

    @Override
    @Transactional
    public CidadeResponseDTO update(Long id, @Valid CidadeDTO cidadeDTO) throws ConstraintViolationException {
        
        validar(cidadeDTO);

        Cidade cidade = cidadeRepository.findById(id);

        if (cidade == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        cidade.setNome(cidadeDTO.nome());

        cidade.setEstado(estadoRepository.findById(cidadeDTO.estado()));

        return new CidadeResponseDTO(cidade);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Cidade cidade = cidadeRepository.findById(id);

        if (cidadeRepository.isPersistent(cidade))
            cidadeRepository.delete(cidade);

        else
            throw new NotFoundException("Nenhuma cidade encontrado");
    }

    @Override
    public List<CidadeResponseDTO> getByNome(String nome, int page, int pageSize) {
        
        return cidadeRepository.findByNome(nome, sort).page(page, pageSize).stream().map(CidadeResponseDTO::new).toList();        
    }

    @Override
    public Long count() {

        return cidadeRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return cidadeRepository.findByNome(nome, sort).count();
    }
    
    private void validar(CidadeDTO cidadeDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<CidadeDTO>> violations = validator.validate(cidadeDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
