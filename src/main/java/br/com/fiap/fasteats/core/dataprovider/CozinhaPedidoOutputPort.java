package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface CozinhaPedidoOutputPort {
    Pedido receberPedido(Pedido pedido);

    Pedido iniciarPreparoPedido(Pedido pedido);

    Pedido finalizarPreparoPedido(Pedido pedido);

    Pedido retirarPedido(Pedido pedido);
}
