package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.ConcluirPedidoPagoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;

import java.time.LocalDateTime;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;

public class AlterarPedidoStatusUseCase implements AlterarPedidoStatusInputPort {
    private final AlterarPedidoStatusValidator alterarPedidoStatusValidator;
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final PedidoOutputPort pedidoOutputPort;
    private final PedidoInputPort pedidoInputPort;
    private final ConcluirPedidoPagoOutputPort concluirPedidoPagoOutputPort;

    public AlterarPedidoStatusUseCase(AlterarPedidoStatusValidator alterarPedidoStatusValidator,
                                      StatusPedidoInputPort statusPedidoInputPort,
                                      PedidoOutputPort pedidoOutputPort,
                                      PedidoInputPort pedidoInputPort,
                                      ConcluirPedidoPagoOutputPort concluirPedidoPagoOutputPort) {
        this.alterarPedidoStatusValidator = alterarPedidoStatusValidator;
        this.statusPedidoInputPort = statusPedidoInputPort;
        this.pedidoOutputPort = pedidoOutputPort;
        this.pedidoInputPort = pedidoInputPort;
        this.concluirPedidoPagoOutputPort = concluirPedidoPagoOutputPort;
    }

    @Override
    public Pedido criado(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarCriado(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_CRIADO);
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    @Override
    public Pedido aguardandoPagamento(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarAguardandoPagamento(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    @Override
    public Pedido pago(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarPago(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_PAGO);
        return formatarPedido(concluirPedidoPagoOutputPort.concluirPagamento(pedidoAtualizado));
    }

    @Override
    public Pedido recebido(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarRecebido(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_RECEBIDO);
        pedidoAtualizado.setDataHoraRecebimento(LocalDateTime.now());
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    @Override
    public Pedido emPreparo(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarEmPreparo(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_EM_PREPARO);
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    @Override
    public Pedido pronto(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarPronto(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_PRONTO);
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    @Override
    public Pedido finalizado(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarFinalizado(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_FINALIZADO);
        pedidoAtualizado.setDataHoraFinalizado(LocalDateTime.now());
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    @Override
    public Pedido cancelado(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarCancelado(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorNomeStatus(pedido, STATUS_PEDIDO_CANCELADO);
        pedido.setDataHoraFinalizado(LocalDateTime.now());
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    @Override
    public Pedido atualizarStatusPedido(Long pedidoId, Long idStatus) {
        String statusPedidoNome = statusPedidoInputPort.consultar(idStatus).getNome();
        return verificarAtualizarStatusPedido(pedidoId, statusPedidoNome);
    }

    private Pedido recuperarPedido(Long pedidoId) {
        return pedidoOutputPort.consultarPedido(pedidoId)
                .orElseThrow(() -> new PedidoNotFound("Pedido não encontrado id " + pedidoId));
    }

    private Pedido atualizarStatusPedidoPorNomeStatus(Pedido pedido, String novoStatusPedido) {
        pedido.setStatusPedido(statusPedidoInputPort.consultarPorNome(novoStatusPedido).getId());
        return pedido;
    }

    private Pedido formatarPedido(Pedido pedido) {
        return pedidoInputPort.formatarPedido(pedido);
    }

    private Pedido verificarAtualizarStatusPedido(Long pedidoId, String novoStatusPedido) {
        return switch (novoStatusPedido) {
            case STATUS_PEDIDO_CRIADO -> criado(pedidoId);
            case STATUS_PEDIDO_AGUARDANDO_PAGAMENTO -> aguardandoPagamento(pedidoId);
            case STATUS_PEDIDO_PAGO -> pago(pedidoId);
            case STATUS_PEDIDO_RECEBIDO -> recebido(pedidoId);
            case STATUS_PEDIDO_EM_PREPARO -> emPreparo(pedidoId);
            case STATUS_PEDIDO_PRONTO -> pronto(pedidoId);
            case STATUS_PEDIDO_FINALIZADO -> finalizado(pedidoId);
            case STATUS_PEDIDO_CANCELADO -> cancelado(pedidoId);
            default -> throw new RegraNegocioException("Status do pedido inválido");
        };
    }
}
