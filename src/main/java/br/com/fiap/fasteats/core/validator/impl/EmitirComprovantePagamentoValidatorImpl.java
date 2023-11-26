package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.StatusPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.EmitirComprovantePagamentoValidator;

import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_PAGO;
import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_PAGO;

public class EmitirComprovantePagamentoValidatorImpl implements EmitirComprovantePagamentoValidator {
    private final PedidoInputPort pedidoInputPort;
    private final PagamentoInputPort pagamentoInputPort;
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final StatusPagamentoInputPort statusPagamentoInputPort;

    public EmitirComprovantePagamentoValidatorImpl(PedidoInputPort pedidoInputPort,
                                                   PagamentoInputPort pagamentoInputPort,
                                                   StatusPedidoInputPort statusPedidoInputPort, StatusPagamentoInputPort statusPagamentoInputPort) {
        this.pedidoInputPort = pedidoInputPort;
        this.pagamentoInputPort = pagamentoInputPort;
        this.statusPedidoInputPort = statusPedidoInputPort;
        this.statusPagamentoInputPort = statusPagamentoInputPort;
    }

    @Override
    public void validarEmitirComprovantePagamento(Long pedidoId) {
        Pedido pedido = pedidoInputPort.consultar(pedidoId);
        if (!statusPedidoInputPort.consultar(pedido.getStatusPedido()).getNome().equals(STATUS_PEDIDO_PAGO))
            throw new RegraNegocioException("O status do pedido deve ser pago para emissão do comprovante de pagamento");

        Pagamento pagamento = pagamentoInputPort.consultarPorIdPedido(pedidoId);
        if(!statusPagamentoInputPort.consultar(pagamento.getStatusPagamento().getId()).getNome().equals(STATUS_PAGO)){
            throw new RegraNegocioException("O status do pagamento deve ser pago para emissão do comprovante de pagamento");
        }
    }
}
