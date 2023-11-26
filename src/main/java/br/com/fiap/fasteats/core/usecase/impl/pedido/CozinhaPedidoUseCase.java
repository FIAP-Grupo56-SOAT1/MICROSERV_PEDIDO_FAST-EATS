package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.CozinhaPedidoInputPort;


public class CozinhaPedidoUseCase implements CozinhaPedidoInputPort {
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    public CozinhaPedidoUseCase(AlterarPedidoStatusInputPort alterarPedidoStatusInputPort) {
        this.alterarPedidoStatusInputPort = alterarPedidoStatusInputPort;
    }

    @Override
    public Pedido receberPedido(Long pedidoId) {
        return alterarPedidoStatusInputPort.recebido(pedidoId);
    }

}
