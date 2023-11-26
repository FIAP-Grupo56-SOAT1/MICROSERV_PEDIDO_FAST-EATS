package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.dataprovider.PagamentoExternoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.usecase.pagamento.AlterarPagamentoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.EmitirComprovantePagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoExternoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.validator.CancelarPagamentoValidator;

import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.*;

public class PagamentoExternoUseCase implements PagamentoExternoInputPort {

    private final PagamentoInputPort pagamentoInputPort;
    private final PagamentoExternoOutputPort pagamentoExternoOutputPort;
    private final EmitirComprovantePagamentoInputPort emitirComprovantePagamentoInputPort;
    private final AlterarPagamentoStatusInputPort alterarPagamentoStatusInputPort;
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    private final CancelarPagamentoValidator cancelarPagamentoValidator;

    public PagamentoExternoUseCase(PagamentoInputPort pagamentoInputPort,
                                   PagamentoExternoOutputPort pagamentoExternoOutputPort,
                                   EmitirComprovantePagamentoInputPort emitirComprovantePagamentoInputPort,
                                   AlterarPagamentoStatusInputPort alterarPagamentoStatusInputPort,
                                   AlterarPedidoStatusInputPort alterarPedidoStatusInputPort,
                                   CancelarPagamentoValidator cancelarPagamentoValidator) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.pagamentoExternoOutputPort = pagamentoExternoOutputPort;
        this.emitirComprovantePagamentoInputPort = emitirComprovantePagamentoInputPort;
        this.alterarPagamentoStatusInputPort = alterarPagamentoStatusInputPort;
        this.alterarPedidoStatusInputPort = alterarPedidoStatusInputPort;
        this.cancelarPagamentoValidator = cancelarPagamentoValidator;
    }

    public Pagamento atualizarPagamento(PagamentoExterno pagamentoExternoRequisicao) {
        try {
            Pagamento pagamento = pagamentoInputPort.consultarPorIdPagamentoExterno(pagamentoExternoRequisicao.getId());
            Pagamento pagamentoAtualizadoExterno = pagamentoExternoOutputPort.recuperarPagamentoDePagamentoExterno(pagamentoExternoRequisicao);
            String nomeStatusPagamento = pagamentoAtualizadoExterno.getStatusPagamento().getNome();

            if (nomeStatusPagamento.equals(STATUS_CANCELADO))
                cancelarPagamentoValidator.validarCancelarPagamento(pagamento.getPedido().getId());

            atualizarStatusPagamento(pagamento.getId(), nomeStatusPagamento);
            atualizarStatusPedido(pagamento.getPedido().getId(), nomeStatusPagamento);
            return emitirComprovantePagamento(pagamento.getPedido().getId());
        } catch (Exception e) {
            return new Pagamento();
        }
    }

    @Override
    public void cancelarPagamentoExterno(Long pagamentoExternoId) {
        pagamentoExternoOutputPort.cancelarPagamento(pagamentoExternoId);
    }

    private Pagamento emitirComprovantePagamento(Long pedidoId) {
        Pagamento pagamento = pagamentoInputPort.consultarPorIdPedido(pedidoId);
        if (pagamento.getStatusPagamento().getNome().equals(STATUS_PAGO))
            return emitirComprovantePagamentoInputPort.emitir(pedidoId);
        return pagamento;
    }

    private void atualizarStatusPagamento(Long pedidoId, String nomeStatusPagamento) {
        switch (nomeStatusPagamento) {
            case STATUS_PAGO -> alterarPagamentoStatusInputPort.pago(pedidoId);
            case STATUS_RECUSADO -> alterarPagamentoStatusInputPort.recusado(pedidoId);
            case STATUS_CANCELADO -> alterarPagamentoStatusInputPort.cancelado(pedidoId);
            default -> {
                break;
            }
        }
    }

    private void atualizarStatusPedido(Long pedidoId, String nomeStatusPagamento) {
        switch (nomeStatusPagamento) {
            case STATUS_PAGO -> alterarPedidoStatusInputPort.pago(pedidoId);
            case STATUS_CANCELADO -> alterarPedidoStatusInputPort.cancelado(pedidoId);
            default -> {
                break;
            }
        }
    }
}
