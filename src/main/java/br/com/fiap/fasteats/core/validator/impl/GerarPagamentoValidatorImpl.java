package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pagamento.FormaPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.GerarPagamentoValidator;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_AGUARDANDO_PAGAMENTO;
import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CRIADO;

public class GerarPagamentoValidatorImpl implements GerarPagamentoValidator {
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final FormaPagamentoInputPort formaPagamentoInputPort;
    private final PedidoInputPort pedidoInputPort;

    public GerarPagamentoValidatorImpl(StatusPedidoInputPort statusPedidoInputPort,
                                       FormaPagamentoInputPort formaPagamentoInputPort,
                                       PedidoInputPort pedidoInputPort) {
        this.statusPedidoInputPort = statusPedidoInputPort;
        this.formaPagamentoInputPort = formaPagamentoInputPort;
        this.pedidoInputPort = pedidoInputPort;
    }

    @Override
    public void validarPedidoStatus(Long pedidoId) {
        Pedido pedido = pedidoInputPort.consultar(pedidoId);
        String nomeStatusPedido = statusPedidoInputPort.consultar(pedido.getStatusPedido()).getNome();
        if (!(nomeStatusPedido.equals(STATUS_PEDIDO_CRIADO) || nomeStatusPedido.equals(STATUS_PEDIDO_AGUARDANDO_PAGAMENTO)))
            throw new RegraNegocioException("Só é possível gerar um pagamento quando o pedido está no status CRIADO ou AGUARDANDO_PAGAMENTO");
    }

    @Override
    public void validarFormaPagamento(Long formaPagamentoId) {
        formaPagamentoInputPort.consultar(formaPagamentoId);
    }
}
