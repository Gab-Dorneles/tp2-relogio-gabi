package br.unitins.ecommerce.service.file;

import java.io.File;
import java.io.IOException;

import br.unitins.ecommerce.model.usuario.Usuario;

public interface UserFileService {
    
    Usuario salvar(Long id, String nomeImagem, byte[] imagem) throws IOException;

    File download(String nomeArquivo);
}
