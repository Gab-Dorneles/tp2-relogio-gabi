package br.unitins.ecommerce.service.fabricante;

import java.util.List;

import br.unitins.ecommerce.dto.fabricante.FabricanteDTO;
import br.unitins.ecommerce.dto.fabricante.FabricanteResponseDTO;
import jakarta.validation.Valid;

public interface FabricanteService {
    
    List<FabricanteResponseDTO> getAll();

    List<FabricanteResponseDTO> getAll(int page, int pageSize);
    
    FabricanteResponseDTO getById(Long id);

    FabricanteResponseDTO insert(@Valid FabricanteDTO fabricanteDTO);

    FabricanteResponseDTO update(Long id, @Valid FabricanteDTO fabricanteDTO);

    void delete(Long id);

    List<FabricanteResponseDTO> getByNome(String nome, int page, int pageSize);

    Long count();

    Long countByNome(String nome);
}
