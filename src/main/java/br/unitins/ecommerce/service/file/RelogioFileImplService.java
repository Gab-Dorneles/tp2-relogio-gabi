package br.unitins.ecommerce.service.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.unitins.ecommerce.model.produto.Relogio;
import org.jboss.logging.Logger;

import br.unitins.ecommerce.repository.RelogioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RelogioFileImplService implements FileService {

    // ex. /user/janio/quarkus/images/usuario/
    private final String PATH_USER = System.getProperty("user.home")
        + File.separator + "quarkus"
        + File.separator + "images"
        + File.separator + "relogio" + File.separator;

    @Inject
    RelogioRepository relogioRepository;

    private static final Logger LOG = Logger.getLogger(RelogioFileImplService.class);

    @Override
    @Transactional
    public void salvar(Long id, String nomeImagem, byte[] imagem)  throws IOException {
        Relogio relogio = relogioRepository.findById(id);

        try {
            String novoNomeImagem = salvarImagem(imagem, nomeImagem);
            relogio.setNomeImagem(novoNomeImagem);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    private String salvarImagem(byte[] imagem, String nomeImagem) throws IOException {

        String mimeType = Files.probeContentType(new File(nomeImagem).toPath());
        List<String> listMimeType = Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/gif");
        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Tipo de imagem não suportada.");
        }

        // verificando o tamanho do arquivo, não permitor maior que 10 megas
        if (imagem.length > (1024 * 1024 * 10))
            throw new IOException("Arquivo muito grande.");

        File diretorio = new File(PATH_USER);
        if (!diretorio.exists())
            diretorio.mkdirs();

        String nomeArquivo = UUID.randomUUID()
        +"."+mimeType.substring(mimeType.lastIndexOf("/")+1);

        String path = PATH_USER + nomeArquivo;

        File file = new File(path);
        if (file.exists())
            throw new IOException("O nome gerado da imagem está repetido.");

        // criando um arquivo no S.O.
        file.createNewFile();
        
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagem);
        // garantindo o envio do ultimo lote de bytes
        fos.flush();
        fos.close();

        return nomeArquivo;
    }

    @Override
    public File download(String nomeArquivo) {
        
        File file = new File(PATH_USER+nomeArquivo);
        return file;
    }
    
}
