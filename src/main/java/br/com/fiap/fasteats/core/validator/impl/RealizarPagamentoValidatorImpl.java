package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.RealizarPagamentoValidator;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_AGUARDANDO_PAGAMENTO;

public class RealizarPagamentoValidatorImpl implements RealizarPagamentoValidator {
    private final PedidoInputPort pedidoInputPort;
    private final StatusPedidoInputPort statusPedidoInputPort;

    public RealizarPagamentoValidatorImpl(PedidoInputPort pedidoInputPort, StatusPedidoInputPort statusPedidoInputPort) {
        this.pedidoInputPort = pedidoInputPort;
        this.statusPedidoInputPort = statusPedidoInputPort;
    }

    @Override
    public void validarStatusPedido(Long pedidoId) {
        Pedido pedido = pedidoInputPort.consultar(pedidoId);
        String status = statusPedidoInputPort.consultar(pedido.getStatusPedido()).getNome();
        if (!status.equals(STATUS_PEDIDO_AGUARDANDO_PAGAMENTO)) {
            throw new RegraNegocioException("Só é possível realizar o pagamento de um pedido com status AGUARDANDO_PAGAMENTO");
        }
    }
}
