package br.com.fiap.fasteats.core.validator;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;

public interface ProdutoPedidoValidator {
    void validarAdicionarProduto(Pedido pedido);
    void validarAtualizarProduto(ProdutoPedido produtoPedido, Pedido pedido);
    void validarRemoverProduto(Pedido pedido);
}
