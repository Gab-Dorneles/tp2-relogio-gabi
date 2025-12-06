package br.unitins.ecommerce.service.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.dto.itempedido.ItemPedidoDTO;
import br.unitins.ecommerce.dto.pedido.PedidoDTO;
import br.unitins.ecommerce.dto.pedido.PedidoResponseDTO;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.pedido.ItemPedido;
import br.unitins.ecommerce.model.pedido.Pedido;
import br.unitins.ecommerce.model.pedido.pagamento.BandeiraCartao;
import br.unitins.ecommerce.model.pedido.pagamento.BoletoBancario;
import br.unitins.ecommerce.model.pedido.pagamento.CartaoCredito;
import br.unitins.ecommerce.model.pedido.pagamento.Pix;
import br.unitins.ecommerce.model.produto.Relogio;
import br.unitins.ecommerce.repository.CidadeRepository;
import br.unitins.ecommerce.repository.RelogioRepository;
import br.unitins.ecommerce.repository.PedidoRepository;
import br.unitins.ecommerce.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {
    
    @Inject
    RelogioRepository relogioRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PedidoRepository pedidoRepository;
    
    @Inject
    CidadeRepository cidadeRepository;

    private static final Logger LOG = Logger.getLogger(PedidoServiceImpl.class);

    @Override
    @Transactional
    public PedidoResponseDTO insert(PedidoDTO pedidoDTO, String login) {
        Pedido pedido = new Pedido();
        pedido.setDataHoraPedido(LocalDateTime.now());

        LOG.info(pedidoDTO);
        LOG.info(pedidoDTO.itens());

        // calculo do total do pedido
        Double total = 0.0;
        for (ItemPedidoDTO itemDto : pedidoDTO.itens()) {
            total += (itemDto.preco() * itemDto.quantidade());
        }
        pedido.setTotalPedido(total);

        // adicionando os itens do pedido
        pedido.setItens(new ArrayList<ItemPedido>());
        for (ItemPedidoDTO itemDto : pedidoDTO.itens()) {
            ItemPedido item = new ItemPedido();
            item.setPreco(itemDto.preco());
            item.setQuantidade(itemDto.quantidade());
            LOG.info(itemDto.idRelogio());
            Relogio relogio = relogioRepository.findById(itemDto.idRelogio());
            item.setRelogio(relogio);

            pedido.getItens().add(item);
        }

        // buscando o usuario pelo login
        pedido.setUsuario(usuarioRepository.findByLogin(login));

        switch (pedidoDTO.pagamento()) {
            case 1:
                
                BoletoBancario boletoBancario = new BoletoBancario(pedido.getTotalPedido(), pedido.getUsuario().getNome(), pedido.getUsuario().getCpf());

                pedido.setPagamento(boletoBancario);
            break;
        
            case 2:

                Pix pix = new Pix(pedido.getTotalPedido(), pedido.getUsuario().getNome(), pedido.getUsuario().getCpf());

                pedido.setPagamento(pix);
            break;

            case 3:

                CartaoCredito cartaoCredito = new CartaoCredito(pedido.getTotalPedido(),
                                            pedidoDTO.cartaoCreditoDTO().numeroCartao(),
                                            pedidoDTO.cartaoCreditoDTO().nomeImpressoCartao(),
                                            pedido.getUsuario().getCpf(),
                                            BandeiraCartao.valueOf(pedidoDTO.cartaoCreditoDTO().bandeiraCartao()));
                pedido.setPagamento(cartaoCredito);
            break;
        }

        pedido.setEndereco(new Endereco(pedidoDTO.enderecoDTO(), cidadeRepository.findById(pedidoDTO.enderecoDTO().cidade())));

        // salvando no banco
        pedidoRepository.persist(pedido);  

        return new PedidoResponseDTO(pedido);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        return new PedidoResponseDTO(pedidoRepository.findById(id));
    }

    @Override
    public List<PedidoResponseDTO> findByAll() {
        return pedidoRepository.listAll().stream()
            .map(PedidoResponseDTO::new).toList();
    }

    @Override
    public List<PedidoResponseDTO> findByAll(String login) {
        
        return pedidoRepository.findAll(login).stream()
            .map(PedidoResponseDTO::new).toList();
    }
}
