package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.ProdutoPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.usecase.ProdutoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ProdutoPedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import br.com.fiap.fasteats.core.validator.ProdutoPedidoValidator;

import java.util.Objects;

public class ProdutoPedidoUseCase implements ProdutoPedidoInputPort {
    private final PedidoInputPort pedidoInputPort;
    private final ProdutoPedidoOutputPort produtoPedidoOutputPort;
    private final ProdutoInputPort produtoInputPort;
    private final PedidoValidator pedidoValidator;
    private final ProdutoPedidoValidator produtoPedidoValidator;

    public ProdutoPedidoUseCase(PedidoInputPort pedidoInputPort, ProdutoPedidoOutputPort produtoPedidoOutputPort,
                                ProdutoInputPort produtoInputPort, PedidoValidator pedidoValidator,
                                ProdutoPedidoValidator produtoPedidoValidator) {
        this.pedidoInputPort = pedidoInputPort;
        this.produtoPedidoOutputPort = produtoPedidoOutputPort;
        this.produtoInputPort = produtoInputPort;
        this.pedidoValidator = pedidoValidator;
        this.produtoPedidoValidator = produtoPedidoValidator;
    }

    @Override
    public Pedido adicionarProdutoPedido(ProdutoPedido produtoPedido) {
        Pedido pedido = recuperarPedido(produtoPedido);
        pedidoValidator.validarAlterarPedido(pedido);
        produtoPedidoValidator.validarAdicionarProduto(pedido);
        Produto produto = recuperarProduto(produtoPedido);

        if (produtoExisteNoPedido(pedido, produtoPedido))
            throw new RegraNegocioException("Produto já existe no pedido id " + produtoPedido.getIdProduto());

        ProdutoPedido novoProdutoPedido = adicionarProdutoPedido(Objects.requireNonNull(produto), new ProdutoPedido(), produtoPedido);
        pedido.getProdutos().add(novoProdutoPedido);
        pedidoInputPort.atualizarValorPedido(pedido);
        return pedidoInputPort.atualizar(pedido);
    }

    @Override
    public Pedido atualizarProdutoPedido(ProdutoPedido produtoPedido) {
        Pedido pedido = recuperarPedido(produtoPedido);
        validarAlterarPedidoEProdutoPedido(pedido, produtoPedido);
        Produto produto = recuperarProduto(produtoPedido);

        if (!produtoExisteNoPedido(pedido, produtoPedido))
            throw new RegraNegocioException("Produto não existe no pedido id " + produtoPedido.getIdProduto());

        pedido.getProdutos()
                .stream()
                .filter(produtoDoPedido -> produtoDoPedido.getIdProduto().equals(produtoPedido.getIdProduto()))
                .findFirst()
                .ifPresent(produtoDoPedido -> atualizarProdutoPedido(Objects.requireNonNull(produto), produtoDoPedido, produtoPedido));

        pedidoInputPort.atualizarValorPedido(pedido);
        return pedidoInputPort.atualizar(pedido);
    }

    @Override
    public Pedido removerProdutoPedido(ProdutoPedido produtoPedido) {
        ProdutoPedido produtoPedidoConsulta = consultarProdutoPedido(produtoPedido);
        Pedido pedido = recuperarPedido(produtoPedido);
        pedidoValidator.validarAlterarPedido(pedido);
        produtoPedidoValidator.validarRemoverProduto(pedido);
        produtoPedidoOutputPort.removerProdutoPedido(produtoPedidoConsulta);
        Pedido pedidoAtualizado = recuperarPedido(produtoPedido);
        pedidoAtualizado.getProdutos().removeIf(produtoDoPedido ->
                produtoDoPedido.getIdProduto().equals(produtoPedido.getIdProduto()));
        pedidoInputPort.atualizarValorPedido(pedidoAtualizado);
        return pedidoInputPort.atualizar(pedidoAtualizado);
    }

    private ProdutoPedido adicionarProdutoPedido(Produto produto, ProdutoPedido produtoDoPedido, ProdutoPedido produtoPedidoRequisicao) {
        if (produtoDoPedido.getQuantidade() == null) produtoDoPedido.setQuantidade(1);
        produtoDoPedido.setQuantidade(produtoDoPedido.getQuantidade() + produtoPedidoRequisicao.getQuantidade());
        produtoDoPedido.setValor(produto.getValor());
        produtoDoPedido.setIdPedido(produtoPedidoRequisicao.getIdPedido());
        produtoDoPedido.setIdProduto(produto.getId());
        return produtoDoPedido;
    }

    private void atualizarProdutoPedido(Produto produto, ProdutoPedido produtoDoPedido, ProdutoPedido produtoPedidoRequisicao) {
        produtoDoPedido.setQuantidade(produtoPedidoRequisicao.getQuantidade());
        produtoDoPedido.setValor(produto.getValor());
        produtoDoPedido.setIdPedido(produtoPedidoRequisicao.getIdPedido());
        produtoDoPedido.setIdProduto(produto.getId());
    }

    private void validarAlterarPedidoEProdutoPedido(Pedido pedido, ProdutoPedido produtoPedido) {
        pedidoValidator.validarAlterarPedido(pedido);
        produtoPedidoValidator.validarAtualizarProduto(produtoPedido, pedido);
    }

    private boolean produtoExisteNoPedido(Pedido pedido, ProdutoPedido produtoPedido) {
        return pedido.getProdutos()
                .stream()
                .anyMatch(produtoDoPedido -> produtoDoPedido.getIdProduto().equals(produtoPedido.getIdProduto()));
    }

    private Produto recuperarProduto(ProdutoPedido produtoPedido) {
        return produtoInputPort.consultar(produtoPedido.getIdProduto());
    }

    private Pedido recuperarPedido(ProdutoPedido produtoPedido) {
        return pedidoInputPort.consultar(produtoPedido.getIdPedido());
    }

    private ProdutoPedido consultarProdutoPedido(ProdutoPedido produtoPedido) {
        return produtoPedidoOutputPort.consultarProdutoPedido(produtoPedido).
                orElseThrow(() -> new ProdutoNotFound("Produto não encontrado no pedido id " + produtoPedido.getIdPedido()));
    }
}
