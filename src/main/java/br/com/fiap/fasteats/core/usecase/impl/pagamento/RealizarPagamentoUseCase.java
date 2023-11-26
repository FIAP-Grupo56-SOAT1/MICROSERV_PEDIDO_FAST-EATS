package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.*;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.RealizarPagamentoValidator;

public class RealizarPagamentoUseCase implements RealizarPagamentoInputPort {
    private final PedidoInputPort pedidoInputPort;
    private final FormaPagamentoInputPort formaPagamentoInputPort;
    private final AlterarPagamentoStatusInputPort alterarPagamentoStatusInputPort;
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    private final PagamentoInputPort pagamentoInputPort;
    private final EmitirComprovantePagamentoInputPort emitirComprovantePagamentoInputPort;
    private final RealizarPagamentoValidator realizarPagamentoValidator;

    public RealizarPagamentoUseCase(PedidoInputPort pedidoInputPort,
                                    FormaPagamentoInputPort formaPagamentoInputPort,
                                    AlterarPagamentoStatusInputPort alterarPagamentoStatusInputPort,
                                    AlterarPedidoStatusInputPort alterarPedidoStatusInputPort,
                                    PagamentoInputPort pagamentoInputPort,
                                    EmitirComprovantePagamentoInputPort emitirComprovantePagamentoInputPort,
                                    RealizarPagamentoValidator realizarPagamentoValidator) {
        this.pedidoInputPort = pedidoInputPort;
        this.formaPagamentoInputPort = formaPagamentoInputPort;
        this.alterarPagamentoStatusInputPort = alterarPagamentoStatusInputPort;
        this.alterarPedidoStatusInputPort = alterarPedidoStatusInputPort;
        this.pagamentoInputPort = pagamentoInputPort;
        this.emitirComprovantePagamentoInputPort = emitirComprovantePagamentoInputPort;
        this.realizarPagamentoValidator = realizarPagamentoValidator;
    }

    @Override
    public Pagamento realizarPagamento(Long pedidoId) {
        realizarPagamentoValidator.validarStatusPedido(pedidoId);
        Pagamento pagamento = pagamentoInputPort.consultarPorIdPedido(pedidoInputPort.consultar(pedidoId).getId());

        if (Boolean.TRUE.equals(formaPagamentoInputPort.consultar(pagamento.getFormaPagamento().getId()).getExterno()))
            throw new RegraNegocioException("O Pagamento deve ser realizado externamente");

        Pagamento pagamentoRealizado = alterarPagamentoStatusInputPort.pago(pagamento.getId());
        alterarPedidoStatusInputPort.pago(pedidoId);
        emitirComprovantePagamentoInputPort.emitir(pedidoId);
        return pagamentoRealizado;
    }
}
