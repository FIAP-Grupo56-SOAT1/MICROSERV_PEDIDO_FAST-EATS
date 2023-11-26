package br.com.fiap.fasteats.core.usecase.impl.unit;

import br.com.fiap.fasteats.core.dataprovider.ProdutoPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.ProdutoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ProdutoPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ProdutoPedidoUseCase;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import br.com.fiap.fasteats.core.validator.ProdutoPedidoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ProdutoPedidoUseCaseUnitTest")
class ProdutoPedidoUseCaseUnitTest {
    private ProdutoPedidoInputPort produtoPedidoUseCase;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produtoPedidoUseCase = new ProdutoPedidoUseCase(
                pedidoInputPort,
                produtoPedidoOutputPort,
                produtoInputPort,
                pedidoValidator,
                produtoPedidoValidator
        );
    }

    @Test
    @DisplayName("Deve adicionar um produto a um pedido")
    void testeAdicionarProdutoAoPedido() {
        Long idPedido = 1L;
        Long idProduto = 2L;
        Long idStatusPedido = 2L;
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(idPedido);
        produtoPedido.setIdProduto(idProduto);
        produtoPedido.setQuantidade(1);

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);
        pedido.setProdutos(new ArrayList<>());

        Produto produto = new Produto();
        produto.setId(idProduto);
        produto.setValor(10.0);

        Pedido pedidoModificado = new Pedido();
        pedidoModificado.setId(idPedido);
        pedidoModificado.setStatusPedido(idStatusPedido);
        pedidoModificado.getProdutos().add(produtoPedido);

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        when(produtoInputPort.consultar(idProduto)).thenReturn(produto);
        when(pedidoInputPort.atualizar(pedido)).thenReturn(pedidoModificado);

        Pedido resultado = produtoPedidoUseCase.adicionarProdutoPedido(produtoPedido);

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        assertNotNull(resultado.getProdutos());
        assertEquals(1, resultado.getProdutos().size());

        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(produtoInputPort, times(1)).consultar(idProduto);
        verify(pedidoInputPort, times(1)).atualizar(pedido);
    }

    @Test
    @DisplayName("Deve atualizar um produto em um pedido")
    void testeAtualizarProdutoNoPedido() {
        Long idPedido = 1L;
        Long idProduto = 2L;
        Long idStatusPedido = 2L;
        ProdutoPedido produtoPedidoSalvo = new ProdutoPedido();
        produtoPedidoSalvo.setIdPedido(idPedido);
        produtoPedidoSalvo.setIdProduto(idProduto);
        produtoPedidoSalvo.setQuantidade(1);

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(idPedido);
        produtoPedido.setIdProduto(idProduto);
        produtoPedido.setQuantidade(2);

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);
        pedido.getProdutos().add(produtoPedidoSalvo);

        Produto produto = new Produto();
        produto.setId(idProduto);
        produto.setValor(10.0);

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(idPedido);
        pedidoSalvo.setStatusPedido(idStatusPedido);
        pedidoSalvo.getProdutos().add(produtoPedido);

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        when(produtoInputPort.consultar(idProduto)).thenReturn(produto);
        when(pedidoInputPort.atualizar(pedido)).thenReturn(pedidoSalvo);

        Pedido resultado = produtoPedidoUseCase.atualizarProdutoPedido(produtoPedido);

        assertNotNull(resultado);
        assertNotNull(resultado.getProdutos());
        assertEquals(1, resultado.getProdutos().size());

        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(produtoInputPort, times(1)).consultar(idProduto);
        verify(pedidoInputPort, times(1)).atualizar(pedido);
    }

    @Test
    @DisplayName("Deve remover um produto de um pedido")
    void testeRemoverProdutoDoPedido() {
        Long idPedido = 1L;
        Long idProduto = 2L;
        Long idStatusPedido = 2L;
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(idPedido);
        produtoPedido.setIdProduto(idProduto);

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);
        pedido.getProdutos().add(produtoPedido);

        ProdutoPedido produtoPedidoConsulta = new ProdutoPedido();
        produtoPedidoConsulta.setIdPedido(idPedido);
        produtoPedidoConsulta.setIdProduto(idProduto);

        Pedido pedidoAtualizado = new Pedido();
        pedidoAtualizado.setId(idPedido);
        pedidoAtualizado.setStatusPedido(idStatusPedido);

        when(produtoPedidoOutputPort.consultarProdutoPedido(produtoPedidoConsulta)).thenReturn(Optional.of(produtoPedidoConsulta));
        doNothing().when(produtoPedidoOutputPort).removerProdutoPedido(produtoPedidoConsulta);
        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        when(pedidoInputPort.atualizar(pedido)).thenReturn(pedidoAtualizado);

        Pedido resultado = produtoPedidoUseCase.removerProdutoPedido(produtoPedido);

        assertNotNull(resultado);
        assertTrue(resultado.getProdutos().isEmpty());

        verify(pedidoInputPort, times(2)).consultar(idPedido);
        verify(pedidoValidator, times(1)).validarAlterarPedido(pedido);
        verify(produtoPedidoOutputPort, times(1)).removerProdutoPedido(produtoPedidoConsulta);
        verify(pedidoInputPort, times(1)).atualizar(pedido);
    }
}
