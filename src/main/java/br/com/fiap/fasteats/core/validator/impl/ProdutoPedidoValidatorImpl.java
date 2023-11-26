package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.dataprovider.ProdutoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.ProdutoPedidoValidator;

import java.util.Objects;
import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CRIADO;

public class ProdutoPedidoValidatorImpl implements ProdutoPedidoValidator {
    private final ProdutoOutputPort produtoOutputPort;
    private final StatusPedidoInputPort statusPedidoInputPort;

    public ProdutoPedidoValidatorImpl(ProdutoOutputPort produtoOutputPort, StatusPedidoInputPort statusPedidoInputPort) {
        this.produtoOutputPort = produtoOutputPort;
        this.statusPedidoInputPort = statusPedidoInputPort;
    }

    @Override
    public void validarAdicionarProduto(Pedido pedido) {
        validarStatusPedido(pedido.getStatusPedido());
    }

    @Override
    public void validarAtualizarProduto(ProdutoPedido produtoPedido, Pedido pedido) {
        validarStatusPedido(pedido.getStatusPedido());
        Optional<Produto> produtoOptional = produtoOutputPort.consultar(produtoPedido.getIdProduto());
        if (produtoPedido.getQuantidade() <= 0)
            throw new RegraNegocioException("Quantidade deve ser maior que " + produtoPedido.getQuantidade());
        if (produtoOptional.isEmpty())
            throw new PedidoNotFound("Produto não encontrado id " + produtoPedido.getIdProduto());
    }

    @Override
    public void validarRemoverProduto(Pedido pedido) {
        validarStatusPedido(pedido.getStatusPedido());
    }

    private void validarStatusPedido(Long statusPedidoId) {
        StatusPedido statusPedido = statusPedidoInputPort.consultar(statusPedidoId);
        if (!Objects.equals(statusPedido.getNome(), STATUS_PEDIDO_CRIADO))
            throw new RegraNegocioException("Este pedido não pode ser alterado, pois está " + statusPedido.getNome());
    }
}
