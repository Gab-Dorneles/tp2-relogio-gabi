package br.unitins.ecommerce.service.relogio;

import java.util.List;

import br.unitins.ecommerce.dto.relogio.RelogioDTO;
import br.unitins.ecommerce.dto.relogio.RelogioResponseDTO;
import br.unitins.ecommerce.model.produto.Relogio;
import jakarta.validation.Valid;

public interface RelogioService {
    
    List<RelogioResponseDTO> getAll();
    
    List<RelogioResponseDTO> getAll(int page, int pageSize);
    
    RelogioResponseDTO getById(Long id);

    RelogioResponseDTO insert(@Valid RelogioDTO relogioDTO);

    RelogioResponseDTO update(Long id, @Valid RelogioDTO relogioDTO);

    void delete(Long id);

    RelogioResponseDTO salvarImage(Long id, String nomeImagem);

    List<RelogioResponseDTO> getByNome(String nome, int page, int pageSize);

    Long count();

    Long countByNome(String nome);

    byte[] criarRelatorioRelogios(String filtro);

    byte[] gerarPdf(List<Relogio> relogios);
}
