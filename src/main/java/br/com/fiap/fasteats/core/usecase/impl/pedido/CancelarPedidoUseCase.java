package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.CancelarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;

public class CancelarPedidoUseCase implements CancelarPedidoInputPort {
    private final PedidoInputPort pedidoInputPort;
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    public CancelarPedidoUseCase(PedidoInputPort pedidoInputPort, AlterarPedidoStatusInputPort alterarPedidoStatusInputPort) {
        this.pedidoInputPort = pedidoInputPort;
        this.alterarPedidoStatusInputPort = alterarPedidoStatusInputPort;
    }

    @Override
    public Pedido cancelar(Long idPedido) {
        Pedido pedido = pedidoInputPort.consultar(idPedido);
        return alterarPedidoStatusInputPort.cancelado(pedido.getId());
    }
}
