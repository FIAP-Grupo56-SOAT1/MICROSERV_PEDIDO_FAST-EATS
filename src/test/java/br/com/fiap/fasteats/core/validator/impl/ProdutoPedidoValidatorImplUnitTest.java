package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.dataprovider.ProdutoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CRIADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProdutoPedidoValidatorImplUnitTest {
    @Mock
    private ProdutoOutputPort produtoOutputPort;
    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;
    @InjectMocks
    private ProdutoPedidoValidatorImpl produtoPedidoValidatorImpl;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void validarAdicionarProduto() {
        // Arrange
        Long statusPedidoId = 1L;
        Pedido pedido = getPedido(statusPedidoId);
        StatusPedido statusPedido = getStatusPedido(statusPedidoId, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultar(statusPedidoId)).thenReturn(statusPedido);

        // Act
        produtoPedidoValidatorImpl.validarAdicionarProduto(pedido);

        // Assert
        verify(statusPedidoInputPort).consultar(statusPedidoId);
    }

    @Test
    void validarAdicionarProdutoRegraNegocioException() {
        // Arrange
        Long statusPedidoId = 1L;
        Pedido pedido = getPedido(statusPedidoId);
        StatusPedido statusPedido = getStatusPedido(statusPedidoId, "TESTE");

        when(statusPedidoInputPort.consultar(statusPedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> produtoPedidoValidatorImpl.validarAdicionarProduto(pedido));
        verify(statusPedidoInputPort).consultar(statusPedidoId);
    }

    @Test
    void validarAtualizarProduto() {
        // Arrange
        Long statusPedidoId = 1L;
        Pedido pedido = getPedido(statusPedidoId);
        StatusPedido statusPedido = getStatusPedido(statusPedidoId, STATUS_PEDIDO_CRIADO);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("TESTE");
        produto.setValor(10.0);
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdProduto(1L);
        produtoPedido.setQuantidade(1);

        when(statusPedidoInputPort.consultar(statusPedidoId)).thenReturn(statusPedido);
        when(produtoOutputPort.consultar(produtoPedido.getIdProduto())).thenReturn(Optional.of(produto));

        // Act
        produtoPedidoValidatorImpl.validarAtualizarProduto(produtoPedido, pedido);

        // Assert
        verify(statusPedidoInputPort).consultar(statusPedidoId);
        verify(produtoOutputPort).consultar(produtoPedido.getIdProduto());
    }

    @Test
    void validarAtualizarProdutoErroQtd() {
        // Arrange
        Long statusPedidoId = 1L;
        Pedido pedido = getPedido(statusPedidoId);
        StatusPedido statusPedido = getStatusPedido(statusPedidoId, STATUS_PEDIDO_CRIADO);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("TESTE");
        produto.setValor(10.0);
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdProduto(1L);
        produtoPedido.setQuantidade(0);

        when(statusPedidoInputPort.consultar(statusPedidoId)).thenReturn(statusPedido);
        when(produtoOutputPort.consultar(produtoPedido.getIdProduto())).thenReturn(Optional.of(produto));

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> produtoPedidoValidatorImpl.validarAtualizarProduto(produtoPedido, pedido));
        verify(statusPedidoInputPort).consultar(statusPedidoId);
        verify(produtoOutputPort).consultar(produtoPedido.getIdProduto());
    }

    @Test
    void validarAtualizarProdutoErroProdutoNaoEncontrado() {
        // Arrange
        Long statusPedidoId = 1L;
        Pedido pedido = getPedido(statusPedidoId);
        StatusPedido statusPedido = getStatusPedido(statusPedidoId, STATUS_PEDIDO_CRIADO);
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdProduto(1L);
        produtoPedido.setQuantidade(0);

        when(statusPedidoInputPort.consultar(statusPedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> produtoPedidoValidatorImpl.validarAtualizarProduto(produtoPedido, pedido));
        verify(statusPedidoInputPort).consultar(statusPedidoId);
        verify(produtoOutputPort).consultar(produtoPedido.getIdProduto());
    }

    @Test
    void validarRemoverProduto() {
        // Arrange
        Long statusPedidoId = 1L;
        Pedido pedido = getPedido(statusPedidoId);
        StatusPedido statusPedido = getStatusPedido(statusPedidoId, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultar(statusPedidoId)).thenReturn(statusPedido);

        // Act
        produtoPedidoValidatorImpl.validarRemoverProduto(pedido);

        // Assert
        verify(statusPedidoInputPort).consultar(statusPedidoId);
    }

    private Pedido getPedido(Long statusPedidoId) {
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(statusPedidoId);
        return pedido;
    }

    private StatusPedido getStatusPedido(Long statusPedidoId, String statusPedidoNome) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(statusPedidoId);
        statusPedido.setNome(statusPedidoNome);
        return statusPedido;
    }
}