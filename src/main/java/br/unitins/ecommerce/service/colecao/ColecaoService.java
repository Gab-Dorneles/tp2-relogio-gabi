package br.unitins.ecommerce.service.colecao;

import java.util.List;
import jakarta.validation.Valid;
import br.unitins.ecommerce.dto.colecao.ColecaoDTO;
import br.unitins.ecommerce.dto.colecao.ColecaoResponseDTO;

public interface ColecaoService {
    
    List<ColecaoResponseDTO> getAll();
    
    List<ColecaoResponseDTO> getAll(int page, int pageSize);
    
    ColecaoResponseDTO getById(Long id);

    ColecaoResponseDTO insert(@Valid ColecaoDTO colecaoDTO);

    ColecaoResponseDTO update(Long id, @Valid ColecaoDTO colecaoDTO);

    void delete(Long id);

    List<ColecaoResponseDTO> getByNome(String nome, int page, int pageSize);

    Long count();

    Long countByNome(String nome);
}
