package br.unitins.ecommerce.service.relogio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.ecommerce.model.produto.Colecao;
import br.unitins.ecommerce.model.produto.Fabricante;
import br.unitins.ecommerce.model.produto.tipoRelogio.TipoRelogio;
import br.unitins.ecommerce.repository.FabricanteRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import br.unitins.ecommerce.dto.relogio.RelogioDTO;
import br.unitins.ecommerce.dto.relogio.RelogioResponseDTO;
import br.unitins.ecommerce.model.produto.Relogio;
import br.unitins.ecommerce.repository.TipoRelogioRepository;
import br.unitins.ecommerce.repository.RelogioRepository;
import br.unitins.ecommerce.repository.ColecaoRepository;
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
public class RelogioImplService implements RelogioService {

    @Inject
    Validator validator;

    @Inject
    RelogioRepository relogioRepository;

    @Inject
    TipoRelogioRepository tipoRelogioRepository;

    @Inject
    ColecaoRepository colecaoRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    private DateTimeFormatter formatterGetAll = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeFormatter formatterGetById = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Sort sort = Sort.by("id").ascending();

    @Override
    public List<RelogioResponseDTO> getAll() {

        return relogioRepository.findAll(sort).stream().map(r -> new RelogioResponseDTO(r, formatterGetAll)).toList();
    }

    @Override
    public List<RelogioResponseDTO> getAll(int page, int pageSize) {

        return relogioRepository.findAll(sort).page(page, pageSize).stream()
                .map(r -> new RelogioResponseDTO(r, formatterGetAll)).toList();
    }

    @Override
    public RelogioResponseDTO getById(Long id) {

        Relogio relogio = relogioRepository.findById(id);

        if (relogio == null)
            throw new NotFoundException("Não encontrado");

        return new RelogioResponseDTO(relogio, formatterGetById);
    }

    @Override
    @Transactional
    public RelogioResponseDTO insert(@Valid RelogioDTO relogioDTO) throws ConstraintViolationException {

        validar(relogioDTO);

        Relogio relogio = new Relogio();
        relogio.setNome(relogioDTO.nome());
        relogio.setDescricao(relogioDTO.descricao());
        relogio.setPreco(relogioDTO.preco());
        relogio.setAnoLancamento(relogioDTO.anoLancamento());
        relogio.setResistenciaAgua(relogioDTO.resistenciaAgua());
        relogio.setTemDiamante(relogioDTO.temDiamante());
        relogio.setTipoPulseira(relogioDTO.tipoPulseira());

        TipoRelogio tipoRelogio = tipoRelogioRepository.findById(relogioDTO.tipoRelogio());
        if (tipoRelogio != null){
            relogio.setTipoRelogio(tipoRelogio);
        }

        List<Colecao> colecaos = new ArrayList<>();
        for (Long id: relogioDTO.colecaos()) {
            Colecao colecao = colecaoRepository.findById(id);
            colecaos.add(colecao);
        }

        relogio.setColecaos(colecaos);

        Fabricante fabricante = fabricanteRepository.findById(relogioDTO.fabricante());
        if (fabricante != null) {
            relogio.setFabricante(fabricante);
        }

        relogioRepository.persist(relogio);

        return new RelogioResponseDTO(relogio);
    }

    @Override
    @Transactional
    public RelogioResponseDTO update(Long id, @Valid RelogioDTO relogioDTO) throws ConstraintViolationException {

        validar(relogioDTO);
        Relogio relogio = relogioRepository.findById(id);

        if (relogio == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        relogio.setNome(relogioDTO.nome());
        relogio.setDescricao(relogioDTO.descricao());
        relogio.setPreco(relogioDTO.preco());
        relogio.setAnoLancamento(relogioDTO.anoLancamento());
        relogio.setResistenciaAgua(relogioDTO.resistenciaAgua());
        relogio.setTemDiamante(relogioDTO.temDiamante());
        relogio.setTipoPulseira(relogioDTO.tipoPulseira());

        TipoRelogio tipoRelogio = tipoRelogioRepository.findById(relogioDTO.tipoRelogio());
        if (tipoRelogio != null){
            relogio.setTipoRelogio(tipoRelogio);
        }

        final List<Long> idsColecoesPersitos = relogio.getColecaos().stream().map(Colecao::getId).toList();
        final List<Long> idsColecaoParaRemover = idsColecoesPersitos.stream().filter(c -> !relogioDTO.colecaos().contains(c)).toList();
        final List<Long> idsColecaoParaAdicionar = relogioDTO.colecaos().stream().filter(c -> !idsColecoesPersitos.contains(c)).toList();

        for (Long idColecaoRemover: idsColecaoParaRemover) {
            List<Colecao> newColecaos = relogio.getColecaos().stream().filter(c -> !c.getId().equals(idColecaoRemover)).collect(Collectors.toList());
            relogio.setColecaos(newColecaos);
        }

        for (Long idColecao: idsColecaoParaAdicionar) {

            if (relogio.getColecaos().stream().noneMatch(c -> c.getId().equals(idColecao))) {
                Colecao colecao = colecaoRepository.findById(idColecao);
                relogio.getColecaos().add(colecao);
            }
        }

        Fabricante fabricante = fabricanteRepository.findById(relogioDTO.fabricante());
        if (fabricante != null) {
            relogio.setFabricante(fabricante);
        }

        relogioRepository.persist(relogio);

        return new RelogioResponseDTO(relogio);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Relogio relogio = relogioRepository.findById(id);

        if (relogioRepository.isPersistent(relogio))
            relogioRepository.delete(relogio);

        else
            throw new NotFoundException("Nenhum relogio encontrado");
    }

    @Override
    @Transactional
    public RelogioResponseDTO salvarImage(Long id, String nomeImagem) {

        Relogio entity = relogioRepository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return new RelogioResponseDTO(entity);
    }

    @Override
    public List<RelogioResponseDTO> getByNome(String nome, int page, int pageSize) {

        return relogioRepository.findByNome(nome, sort).page(page, pageSize).stream()
                .map(r -> new RelogioResponseDTO(r, formatterGetAll)).toList();
    }

    @Override
    public Long count() {

        return relogioRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return relogioRepository.findByNome(nome, sort).count();
    }

    private void validar(RelogioDTO relogioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<RelogioDTO>> violations = validator.validate(relogioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    public byte[] gerarPdf(List<Relogio> relogios) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter pdfWriter = new PdfWriter(baos);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                Document document = new Document(pdfDocument, PageSize.A4)) {

            Paragraph title = new Paragraph("Relatório de Relogios")
                    .setFontSize(14f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(title);

            HeaderFooterHandler headerFooterHandler = new HeaderFooterHandler();
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, headerFooterHandler);

            Table table = new Table(new float[] { 1, 2, 1 })
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMargin(10);

            table.addCell("ID");
            table.addCell("Título");
            table.addCell("Preço");

            for (Relogio relogio : relogios) {
                Text idText = new Text(relogio.getId().toString())
                        .setFontSize(12f);

                Text nomeText = new Text(relogio.getNome())

                        .setFontSize(12f);

                Text precoText = new Text(relogio.getPreco().toString())

                        .setFontSize(12f);

                table.addCell(new Cell().add(new Paragraph().add(idText)));
                table.addCell(new Cell().add(new Paragraph().add(nomeText)));
                table.addCell(new Cell().add(new Paragraph().add(precoText)));
            }

            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public byte[] criarRelatorioRelogios(String filtro) {

        List<Relogio> relogios;

        if (filtro == null) {

            relogios = relogioRepository.findAll().list();
        }
            
        else {

            relogios = relogioRepository.findByNome(filtro, sort).list();
        }
        return gerarPdf(relogios);
    }

    class HeaderFooterHandler implements IEventHandler {
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNum = pdf.getPageNumber(page);

            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdf);
            canvas.beginText().setFontAndSize(pdf.getDefaultFont(), 12);

            canvas.moveText(34, 20).showText("Página " + pageNum);

            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
            canvas.moveText(500 - 80, 0).showText(dataHora);

            canvas.endText();

        }
    }

}
