package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.StatusPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.CancelarPagamentoValidator;

import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_CANCELADO;
import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CANCELADO;
import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_PAGO;

public class CancelarPagamentoValidatorImpl implements CancelarPagamentoValidator {
    private final PedidoInputPort pedidoInputPort;
    private final PagamentoInputPort pagamentoInputPort;
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final StatusPagamentoInputPort statusPagamentoInputPort;

    public CancelarPagamentoValidatorImpl(PedidoInputPort pedidoInputPort, PagamentoInputPort pagamentoInputPort,
                                          StatusPedidoInputPort statusPedidoInputPort, StatusPagamentoInputPort statusPagamentoInputPort) {
        this.pedidoInputPort = pedidoInputPort;
        this.pagamentoInputPort = pagamentoInputPort;
        this.statusPedidoInputPort = statusPedidoInputPort;
        this.statusPagamentoInputPort = statusPagamentoInputPort;
    }

    @Override
    public void validarCancelarPagamento(Long pedidoId) {
        Pedido pedido = pedidoInputPort.consultar(pedidoId);
        String pedidoStatus = statusPedidoInputPort.consultar(pedido.getStatusPedido()).getNome();

        if (pedidoStatus.equals(STATUS_PEDIDO_CANCELADO))
            throw new PedidoNotFound("O pedido já foi cancelado");

        if (pedidoStatus.equals(STATUS_PEDIDO_PAGO))
            throw new PedidoNotFound("O pedido já foi pago");

        Pagamento pagamento = pagamentoInputPort.consultarPorIdPedido(pedidoId);
        String pagamentoStatus = statusPagamentoInputPort.consultar(pagamento.getStatusPagamento().getId()).getNome();

        if (pagamentoStatus.equals(STATUS_CANCELADO))
            throw new PedidoNotFound("O pagamento já foi cancelado");

        if (pagamentoStatus.equals(STATUS_PEDIDO_PAGO))
            throw new PedidoNotFound("O pagamento já foi pago");
    }
}
