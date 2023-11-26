package br.com.fiap.fasteats.core.usecase.pedido;

import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface CancelarPedidoInputPort {
    Pedido cancelar(Long idPedido);
}
