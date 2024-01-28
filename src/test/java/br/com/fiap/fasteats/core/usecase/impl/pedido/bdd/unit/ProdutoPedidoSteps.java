package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.ProdutoPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.usecase.ProdutoInputPort;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ProdutoPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import br.com.fiap.fasteats.core.validator.ProdutoPedidoValidator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProdutoPedidoSteps {
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private ProdutoPedidoOutputPort produtoPedidoOutputPort;
    @Mock
    private ProdutoInputPort produtoInputPort;
    @Mock
    private PedidoValidator pedidoValidator;
    @Mock
    private ProdutoPedidoValidator produtoPedidoValidator;
    @InjectMocks
    private ProdutoPedidoUseCase produtoPedidoUseCase;
    AutoCloseable openMocks;
    private Produto produto;
    private Pedido pedido;
    private ProdutoPedido produtoPedidoRequisicao;

    @Before
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void close() throws Exception {
        openMocks.close();
    }

    @Dado("que um cliente deseja adicionar um produto a um pedido")
    public void queUmClienteDesejaAdicionarUmProdutoAUmPedido() {
        produtoPedidoRequisicao = new ProdutoPedido();
        produtoPedidoRequisicao.setIdProduto(1L);
        produtoPedidoRequisicao.setIdPedido(1L);
        produtoPedidoRequisicao.setQuantidade(2);
        produtoPedidoRequisicao.setValor(20.0);

        produto = new Produto();
        produto.setId(1L);
        produto.setValor(10.0);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.getProdutos().clear();
        pedido.setValor(0.0);
    }

    @Quando("o cliente fornece as informações do produto e do pedido")
    public void oClienteForneceAsInformacoesDoProdutoEeDoPedido() {
        when(produtoInputPort.consultar(any())).thenReturn(produto);
        when(pedidoInputPort.consultar(any())).thenReturn(pedido);
        when(pedidoInputPort.atualizar(any())).thenReturn(pedido);
    }

    @Entao("o produto é adicionado ao pedido com sucesso")
    public void oProdutoEhAdicionadoAoPedidoComSucesso() {
        Pedido resultado = produtoPedidoUseCase.adicionarProdutoPedido(produtoPedidoRequisicao);
        assertNotNull(resultado);
        assertEquals(1, resultado.getProdutos().size());
        verify(pedidoInputPort, times(1)).atualizar(any());
    }

    @Dado("que um cliente deseja atualizar um produto em um pedido")
    public void queUmClienteDesejaAtualizarUmProdutoEmUmPedido() {
        produtoPedidoRequisicao = new ProdutoPedido();
        produtoPedidoRequisicao.setIdProduto(1L);
        produtoPedidoRequisicao.setIdPedido(1L);
        produtoPedidoRequisicao.setQuantidade(1);
        produtoPedidoRequisicao.setValor(10.0);

        produto = new Produto();
        produto.setId(1L);
        produto.setValor(10.0);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.getProdutos().clear();
        pedido.setValor(0.0);
        pedido.getProdutos().add(produtoPedidoRequisicao);
    }

    @Quando("o cliente fornece as novas informações do produto e do pedido")
    public void oClienteForneceAsNovasInformacoesDoProdutoEeDoPedido() {
        when(produtoInputPort.consultar(any())).thenReturn(produto);
        when(pedidoInputPort.consultar(any())).thenReturn(pedido);
        when(pedidoInputPort.atualizar(any())).thenReturn(pedido);
    }

    @Entao("o produto no pedido é atualizado com sucesso")
    public void oProdutoNoPedidoEhAtualizadoComSucesso() {
        Pedido resultado = produtoPedidoUseCase.atualizarProdutoPedido(produtoPedidoRequisicao);
        assertNotNull(resultado);
        assertEquals(1, resultado.getProdutos().size());
        assertEquals(10.0, resultado.getProdutos().get(0).getValor());
        verify(pedidoInputPort, times(1)).atualizar(any());
    }

    @Dado("que um cliente deseja remover um produto de um pedido")
    public void queUmClienteDesejaRemoverUmProdutoDeUmPedido() {
        produtoPedidoRequisicao = new ProdutoPedido();
        produtoPedidoRequisicao.setIdProduto(1L);
        produtoPedidoRequisicao.setIdPedido(1L);

        produto = new Produto();
        produto.setId(1L);
        produto.setValor(10.0);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.getProdutos().clear();
        pedido.setValor(0.0);
        pedido.getProdutos().add(produtoPedidoRequisicao);
    }

    @Entao("o produto é removido do pedido com sucesso")
    public void oProdutoEhRemovidoDoPedidoComSucesso() {
        when(produtoPedidoOutputPort.consultarProdutoPedido(produtoPedidoRequisicao)).thenReturn(Optional.of(produtoPedidoRequisicao));

        when(pedidoInputPort.consultar(any())).thenReturn(pedido);
        when(pedidoInputPort.atualizar(any())).thenReturn(pedido);

        Pedido resultado = produtoPedidoUseCase.removerProdutoPedido(produtoPedidoRequisicao);
        assertNotNull(resultado);
        assertEquals(0, resultado.getProdutos().size());
        verify(pedidoInputPort, times(1)).atualizar(any());
    }

    @Entao("é exibida uma excessão informando produto não encontrado no pedido")
    public void oProdutoNaoExisteNoPedido() {
        assertThrows(ProdutoNotFound.class, () -> produtoPedidoUseCase.removerProdutoPedido(produtoPedidoRequisicao));
    }
}
