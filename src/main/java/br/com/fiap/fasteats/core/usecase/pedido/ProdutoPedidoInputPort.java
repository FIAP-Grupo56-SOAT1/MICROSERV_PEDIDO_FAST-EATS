package br.com.fiap.fasteats.core.usecase.pedido;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;

public interface ProdutoPedidoInputPort {
    Pedido adicionarProdutoPedido(ProdutoPedido produtoPedido);

    Pedido atualizarProdutoPedido(ProdutoPedido produtoPedido);

    Pedido removerProdutoPedido(ProdutoPedido produtoPedido);
}
