package br.unitins.ecommerce.service.tipoRelogio;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioDTO;
import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioResponseDTO;
import br.unitins.ecommerce.model.produto.tipoRelogio.TipoRelogio;
import br.unitins.ecommerce.repository.TipoRelogioRepository;
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
public class TipoRelogioImplService implements TipoRelogioService {

    @Inject
    Validator validator;

    @Inject
    TipoRelogioRepository tipoRelogioRepository;

    private DateTimeFormatter formatterGetAll = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeFormatter formatterGetById = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Sort sort = Sort.by("id").ascending();

    @Override
    public List<TipoRelogioResponseDTO> getAll() {
        
        return tipoRelogioRepository.findAll(sort).stream().map(TipoRelogioResponseDTO::new).toList();
    }

    @Override
    public List<TipoRelogioResponseDTO> getAll(int page, int pageSize) {
        
        return tipoRelogioRepository.findAll(sort).page(page, pageSize).stream().map(TipoRelogioResponseDTO::new).toList();
    }

    @Override
    public TipoRelogioResponseDTO getById(Long id) {
        
        TipoRelogio tipoRelogio = tipoRelogioRepository.findById(id);

        if (tipoRelogio == null)
            throw new NotFoundException("Não encontrado");

        return new TipoRelogioResponseDTO(tipoRelogio);
    }

    @Override
    @Transactional
    public TipoRelogioResponseDTO insert(@Valid TipoRelogioDTO tipoRelogioDTO) {
        
        validar(tipoRelogioDTO);

        TipoRelogio tipoRelogio = new TipoRelogio();

        tipoRelogio.setNome(tipoRelogioDTO.nome());
        tipoRelogio.setDescricao(tipoRelogioDTO.descricao());

        tipoRelogioRepository.persist(tipoRelogio);

        return new TipoRelogioResponseDTO(tipoRelogio);
    }

    @Override
    @Transactional
    public TipoRelogioResponseDTO update(Long id, @Valid TipoRelogioDTO tipoRelogioDTO) {
        
        validar(tipoRelogioDTO);

        TipoRelogio tipoRelogio = tipoRelogioRepository.findById(id);

        if (tipoRelogio == null)
            throw new NotFoundException("Número fora das opções disponíveis");
        tipoRelogio.setNome(tipoRelogioDTO.nome());
        tipoRelogio.setDescricao(tipoRelogioDTO.descricao());

        return new TipoRelogioResponseDTO(tipoRelogio);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        TipoRelogio tipoRelogio = tipoRelogioRepository.findById(id);

        if (tipoRelogioRepository.isPersistent(tipoRelogio))
            tipoRelogioRepository.delete(tipoRelogio);

        else
            throw new NotFoundException("Nenhum Tipo Relogio encontrado");
    }

    @Override
    public List<TipoRelogioResponseDTO> getByNome(String nome, int page, int pageSize) {
        
        return tipoRelogioRepository.findByNome(nome, sort).page(page, pageSize).stream().map(TipoRelogioResponseDTO::new).toList();
    }

    @Override
    public Long count() {

        return tipoRelogioRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return tipoRelogioRepository.findByNome(nome, sort).count();
    }
    
    private void validar(TipoRelogioDTO tipoRelogioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<TipoRelogioDTO>> violations = validator.validate(tipoRelogioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
