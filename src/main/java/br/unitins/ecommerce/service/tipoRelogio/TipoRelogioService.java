package br.unitins.ecommerce.service.tipoRelogio;

import java.util.List;

import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioDTO;
import br.unitins.ecommerce.dto.tipoRelogio.TipoRelogioResponseDTO;
import jakarta.validation.Valid;

public interface TipoRelogioService {
    
    List<TipoRelogioResponseDTO> getAll();
    
    List<TipoRelogioResponseDTO> getAll(int page, int pageSize);
    
    TipoRelogioResponseDTO getById(Long id);

    TipoRelogioResponseDTO insert(@Valid TipoRelogioDTO tipoRelogioDTO);

    TipoRelogioResponseDTO update(Long id, @Valid TipoRelogioDTO tipoRelogioDTO);

    void delete(Long id);

    List<TipoRelogioResponseDTO> getByNome(String nome, int page, int pageSize);

    Long count();

    Long countByNome(String nome);
}
