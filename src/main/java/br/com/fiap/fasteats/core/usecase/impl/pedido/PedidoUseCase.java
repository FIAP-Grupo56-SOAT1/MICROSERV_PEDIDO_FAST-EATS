package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.fiap.fasteats.core.constants.ClienteConstants.CLIENTE_SEM_IDENTIFICAR;
import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CRIADO;

public class PedidoUseCase implements PedidoInputPort {
    private final PedidoOutputPort pedidoOutputPort;
    private final ClienteInputPort clienteInputPort;
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final PagamentoOutputPort pagamentoOutputPort;
    private final PedidoValidator pedidoValidator;

    public PedidoUseCase(PedidoOutputPort pedidoOutputPort, ClienteInputPort clienteInputPort,
            StatusPedidoInputPort statusPedidoInputPort, PagamentoOutputPort pagamentoOutputPort,
            PedidoValidator pedidoValidator) {
        this.pedidoOutputPort = pedidoOutputPort;
        this.clienteInputPort = clienteInputPort;
        this.statusPedidoInputPort = statusPedidoInputPort;
        this.pagamentoOutputPort = pagamentoOutputPort;
        this.pedidoValidator = pedidoValidator;
    }

    @Override
    public Pedido criar(Pedido pedido) {
        StatusPedido statusPedido = statusPedidoInputPort.consultarPorNome(STATUS_PEDIDO_CRIADO);
        identificarCliente(pedido);
        pedido.setStatusPedido(statusPedido.getId());
        pedido.setDataHoraCriado(LocalDateTime.now());
        return formatarPedido(pedidoOutputPort.salvarPedido(pedido));
    }

    @Override
    public Pedido consultar(Long id) {
        return formatarPedido(pedidoOutputPort.consultarPedido(id)
                .orElseThrow(() -> new PedidoNotFound("Pedido não encontrado id " + id)));
    }

    @Override
    public List<Pedido> listar() {
        List<Pedido> pedidos = pedidoOutputPort.listar();
        pedidos.forEach(this::formatarPedido);
        return pedidos;
    }

    @Override
    public Pedido atualizar(Pedido pedido) {
        pedidoValidator.validarAlterarPedido(pedido);
        return formatarPedido(pedidoOutputPort.salvarPedido(pedido));
    }

    @Override
    public void deletar(Long id) {
        consultar(id);
        pedidoOutputPort.deletar(id);
    }

    @Override
    public void atualizarValorPedido(Pedido pedido) {
        pedido.setValor(pedido.getProdutos().stream().mapToDouble(p -> p.getValor() * p.getQuantidade()).sum());
    }

    private void identificarCliente(Pedido pedido) {

        if (!pedido.isIdentificaCliente()) {
            pedido.setCliente(CLIENTE_SEM_IDENTIFICAR);
            return;
        }

        if (Boolean.TRUE.equals(clienteInputPort.clienteExiste(pedido.getCliente().getCpf()))) {
            pedido.setCliente(clienteInputPort.consultar(pedido.getCliente().getCpf()));
            return;
        }

        Cliente clienteCriado = clienteInputPort.criar(pedido.getCliente());
        pedido.setCliente(clienteCriado);
    }

    private Pedido formatarPedido(Pedido pedido) {
        return formatarPedido(pedido, pagamentoOutputPort);
    }

    public static Pedido formatarPedido(Pedido pedido, PagamentoOutputPort pagamentoInputPort) {
       try {
           Pagamento pagamento = pagamentoInputPort.consultarPorPedidoId(pedido.getId()).orElse(null);
           if (pagamento != null) {
               pedido.setIdPagamentoExterno(pagamento.getIdPagamentoExterno());
               pedido.setUrlPagamento(pagamento.getUrlPagamento());
               pedido.setQrCode(pagamento.getQrCode());
           }
       }catch (Exception ignored){}

        return pedido;
    }
}