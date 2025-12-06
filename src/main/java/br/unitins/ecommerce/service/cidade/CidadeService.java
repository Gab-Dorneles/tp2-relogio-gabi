package br.unitins.ecommerce.service.cidade;

import java.util.List;

import br.unitins.ecommerce.dto.cidade.CidadeDTO;
import br.unitins.ecommerce.dto.cidade.CidadeResponseDTO;
import jakarta.validation.Valid;

public interface CidadeService {
    
    List<CidadeResponseDTO> getAll();
    
    List<CidadeResponseDTO> getAll(int page, int pageSize);
    
    CidadeResponseDTO getById(Long id);

    CidadeResponseDTO insert(@Valid CidadeDTO cidadeDTO);

    CidadeResponseDTO update(Long id, @Valid CidadeDTO cidadeDTO);

    void delete(Long id);

    List<CidadeResponseDTO> getByNome(String nome, int page, int pageSize);

    Long count();

    Long countByNome(String nome);
}
