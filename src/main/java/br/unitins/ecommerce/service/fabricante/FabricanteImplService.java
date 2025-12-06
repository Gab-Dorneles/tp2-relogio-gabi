package br.unitins.ecommerce.service.fabricante;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import br.unitins.ecommerce.dto.fabricante.FabricanteDTO;
import br.unitins.ecommerce.dto.fabricante.FabricanteResponseDTO;
import br.unitins.ecommerce.model.produto.Fabricante;
import br.unitins.ecommerce.repository.FabricanteRepository;
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
public class FabricanteImplService implements FabricanteService {

    @Inject
    Validator validator;

    @Inject
    FabricanteRepository fabricanteRepository;

    private DateTimeFormatter formatterGetAll = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeFormatter formatterGetById = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Sort sort = Sort.by("id").ascending();

    @Override
    public List<FabricanteResponseDTO> getAll() {
        
        return fabricanteRepository.findAll(sort).stream().map(fabricante -> new FabricanteResponseDTO(fabricante, formatterGetAll)).toList();
    }

    @Override
    public List<FabricanteResponseDTO> getAll(int page, int pageSize) {
        
        return fabricanteRepository.findAll(sort).page(page, pageSize).stream().map(fabricante -> new FabricanteResponseDTO(fabricante, formatterGetAll)).toList();
    }

    @Override
    public FabricanteResponseDTO getById(Long id) {
        
        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null)
            throw new NotFoundException("Não encontrado");

        return new FabricanteResponseDTO(fabricante, formatterGetById);
    }

    @Override
    @Transactional
    public FabricanteResponseDTO insert(@Valid FabricanteDTO fabricanteDTO) {
        
        validar(fabricanteDTO);

        Fabricante fabricante = new Fabricante();

        fabricante.setNome(fabricanteDTO.nome());
        fabricante.setAnoFundacao(fabricanteDTO.anoFundacao());
        fabricante.setDescricao(fabricanteDTO.descricao());
        fabricante.setLocalizacao(fabricanteDTO.localizacao());

        fabricanteRepository.persist(fabricante);

        return new FabricanteResponseDTO(fabricante);
    }

    @Override
    @Transactional
    public FabricanteResponseDTO update(Long id, @Valid FabricanteDTO fabricanteDTO) {
        
        validar(fabricanteDTO);

        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        fabricante.setNome(fabricanteDTO.nome());
        fabricante.setAnoFundacao(fabricanteDTO.anoFundacao());
        fabricante.setDescricao(fabricanteDTO.descricao());
        fabricante.setLocalizacao(fabricante.getLocalizacao());

        return new FabricanteResponseDTO(fabricante);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricanteRepository.isPersistent(fabricante))
            fabricanteRepository.delete(fabricante);

        else
            throw new NotFoundException("Nenhum fabricante encontrado");
    }

    public List<FabricanteResponseDTO> getByNome(String nome, int page, int pageSize) {

        return fabricanteRepository.findByNome(nome, sort).page(page, pageSize).stream().map(fabricante -> new FabricanteResponseDTO(fabricante, formatterGetAll)).toList();
    }

    @Override
    public Long count() {

        return fabricanteRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return fabricanteRepository.findByNome(nome, sort).count();
    }
    
    private void validar(FabricanteDTO fabricanteDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FabricanteDTO>> violations = validator.validate(fabricanteDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
