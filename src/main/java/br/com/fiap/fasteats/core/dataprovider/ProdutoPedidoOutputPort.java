package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;

import java.util.Optional;

public interface ProdutoPedidoOutputPort {
    void removerProdutoPedido(ProdutoPedido produtoPedido);

    Optional<ProdutoPedido> consultarProdutoPedido(ProdutoPedido produtoPedido);
}
