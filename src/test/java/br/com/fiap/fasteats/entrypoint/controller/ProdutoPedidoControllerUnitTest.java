package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.usecase.pedido.ProdutoPedidoInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.PedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.ProdutoPedidoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.PedidoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ProdutoPedidoControllerUnitTest {
    @Mock
    private ProdutoPedidoInputPort produtoPedidoInputPort;
    @Mock
    private PedidoMapper pedidoMapper;
    @InjectMocks
    private ProdutoPedidoController produtoPedidoController;

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
    void adicionarProduto_DeveRetornarPedidoResponse_QuandoAdicaoBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        ProdutoPedidoRequest produtoPedidoRequest = new ProdutoPedidoRequest();
        ProdutoPedido produtoPedido = new ProdutoPedido();
        Pedido pedidoAtualizado = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(pedidoMapper.toProdutoPedido(produtoPedidoRequest)).thenReturn(produtoPedido);
        when(produtoPedidoInputPort.adicionarProdutoPedido(produtoPedido)).thenReturn(pedidoAtualizado);
        when(pedidoMapper.toPedidoResponse(pedidoAtualizado)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = produtoPedidoController.adicionarProduto(idPedido, produtoPedidoRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void atualizarProduto_DeveRetornarPedidoResponse_QuandoAtualizacaoBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        Long idProduto = 2L;
        ProdutoPedidoRequest produtoPedidoRequest = new ProdutoPedidoRequest();
        ProdutoPedido produtoPedido = new ProdutoPedido();
        Pedido pedidoAtualizado = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(pedidoMapper.toProdutoPedido(produtoPedidoRequest)).thenReturn(produtoPedido);
        when(produtoPedidoInputPort.atualizarProdutoPedido(produtoPedido)).thenReturn(pedidoAtualizado);
        when(pedidoMapper.toPedidoResponse(pedidoAtualizado)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = produtoPedidoController.atualizarProduto(idPedido, idProduto, produtoPedidoRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void removerProduto_DeveRetornarPedidoResponse_QuandoRemocaoBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        Long idProduto = 2L;
        ProdutoPedido produtoPedido = new ProdutoPedido();
        Pedido pedidoAtualizado = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(pedidoMapper.toProdutoPedido(any(ProdutoPedidoRequest.class))).thenReturn(produtoPedido);
        when(produtoPedidoInputPort.removerProdutoPedido(produtoPedido)).thenReturn(pedidoAtualizado);
        when(pedidoMapper.toPedidoResponse(pedidoAtualizado)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = produtoPedidoController.removerProduto(idPedido, idProduto);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNull(resposta.getBody());
    }
}
