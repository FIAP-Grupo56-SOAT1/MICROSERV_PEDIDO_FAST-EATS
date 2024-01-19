package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;

import java.time.LocalDateTime;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;

public class AlterarPedidoStatusUseCase implements AlterarPedidoStatusInputPort {
    private final AlterarPedidoStatusValidator alterarPedidoStatusValidator;
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final PedidoOutputPort pedidoOutputPort;
    private final PagamentoOutputPort pagamentoOutputPort;

    public AlterarPedidoStatusUseCase(AlterarPedidoStatusValidator alterarPedidoStatusValidator,
            StatusPedidoInputPort statusPedidoInputPort,
            PedidoOutputPort pedidoOutputPort,
            PagamentoOutputPort pagamentoOutputPort) {
        this.alterarPedidoStatusValidator = alterarPedidoStatusValidator;
        this.statusPedidoInputPort = statusPedidoInputPort;
        this.pedidoOutputPort = pedidoOutputPort;
        this.pagamentoOutputPort = pagamentoOutputPort;
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
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
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
        Pedido pedido = recuperarPedido(pedidoId);
        alterarPedidoStatusValidator.validarAguardandoPagamento(pedidoId);
        Pedido pedidoAtualizado = atualizarStatusPedidoPorIdStatus(pedido, idStatus);
        return formatarPedido(pedidoOutputPort.salvarPedido(pedidoAtualizado));
    }

    private Pedido recuperarPedido(Long pedidoId) {
        return pedidoOutputPort.consultarPedido(pedidoId)
                .orElseThrow(() -> new PedidoNotFound("Pedido não encontrado id " + pedidoId));
    }

    private Pedido atualizarStatusPedidoPorNomeStatus(Pedido pedido, String novoStatusPedido) {
        pedido.setStatusPedido(statusPedidoInputPort.consultarPorNome(novoStatusPedido).getId());
        return pedido;
    }

    private Pedido atualizarStatusPedidoPorIdStatus(Pedido pedido, Long idStatus) {
        pedido.setStatusPedido(statusPedidoInputPort.consultar(idStatus).getId());
        return pedido;
    }

    private Pedido formatarPedido(Pedido pedido) {
        return PedidoUseCase.formatarPedido(pedido, pagamentoOutputPort);
    }
}
