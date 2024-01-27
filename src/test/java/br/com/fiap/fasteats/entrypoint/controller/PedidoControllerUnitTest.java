package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.CancelarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ConfirmarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.PedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.PedidoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.PedidoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoControllerUnitTest {
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private ConfirmarPedidoInputPort confirmarPedidoInputPort;
    @Mock
    private CancelarPedidoInputPort cancelarPedidoInputPort;
    @Mock
    private PedidoMapper pedidoMapper;
    @Mock
    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    @InjectMocks
    private PedidoController pedidoController;
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
    void criarPedido_DeveRetornarPedidoResponse_QuandoCriacaoBemSucedida() {
        // Arrange
        PedidoRequest pedidoRequest = new PedidoRequest();
        Pedido pedido = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(pedidoMapper.toPedido(pedidoRequest)).thenReturn(pedido);
        when(pedidoInputPort.criar(pedido)).thenReturn(pedido);
        when(pedidoMapper.toPedidoResponse(pedido)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = pedidoController.criarPedido(pedidoRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void consultarPedido_DeveRetornarPedidoResponse_QuandoConsultaBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        Pedido pedido = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        when(pedidoMapper.toPedidoResponse(pedido)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = pedidoController.consultarPedido(idPedido);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void listarPedidos_DeveRetornarListaPedidoResponse_QuandoListaNaoVazia() {
        // Arrange
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        List<PedidoResponse> pedidoResponses = Collections.singletonList(new PedidoResponse());

        when(pedidoInputPort.listar()).thenReturn(pedidos);
        when(pedidoMapper.toPedidosResponse(pedidos)).thenReturn(pedidoResponses);

        // Act
        ResponseEntity<List<PedidoResponse>> resposta = pedidoController.listarPedidos();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertFalse(resposta.getBody().isEmpty());
    }

    @Test
    void listarPedidos_DeveRetornarListaVazia_QuandoListaVazia() {
        // Arrange
        List<Pedido> pedidos = Collections.emptyList();

        when(pedidoInputPort.listar()).thenReturn(pedidos);

        // Act
        ResponseEntity<List<PedidoResponse>> resposta = pedidoController.listarPedidos();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }

    @Test
    void updatePedido_DeveRetornarPedidoResponse_QuandoAtualizacaoBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        PedidoRequest pedidoRequest = new PedidoRequest();
        Pedido pedido = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(pedidoMapper.toPedido(pedidoRequest)).thenReturn(pedido);
        when(pedidoInputPort.atualizar(pedido)).thenReturn(pedido);
        when(pedidoMapper.toPedidoResponse(pedido)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = pedidoController.updatePedido(idPedido, pedidoRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void deletePedido_DeveRetornarNoContent_QuandoDelecaoBemSucedida() {
        // Arrange
        Long idPedido = 1L;

        // Act
        ResponseEntity<Void> resposta = pedidoController.deletePedido(idPedido);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(pedidoInputPort, times(1)).deletar(idPedido);
    }

    @Test
    void cancelarProduto_DeveRetornarPedidoResponse_QuandoCancelamentoBemSucedido() {
        // Arrange
        Long idPedido = 1L;
        Pedido pedidoCancelado = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(cancelarPedidoInputPort.cancelar(idPedido)).thenReturn(pedidoCancelado);
        when(pedidoMapper.toPedidoResponse(pedidoCancelado)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = pedidoController.cancelarProduto(idPedido);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void confirmarPedido_DeveRetornarPedidoResponse_QuandoConfirmacaoBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        Long tipoPagamentoId = 2L;
        Pedido pedidoConfirmado = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(confirmarPedidoInputPort.confirmar(idPedido, tipoPagamentoId)).thenReturn(pedidoConfirmado);
        when(pedidoMapper.toPedidoResponse(pedidoConfirmado)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = pedidoController.confirmarPedido(idPedido, tipoPagamentoId);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void alterarStatusPedido_DeveRetornarPedidoResponse_QuandoAtualizacaoStatusBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        Long idStatus = 2L;
        Pedido pedido = new Pedido();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(alterarPedidoStatusInputPort.atualizarStatusPedido(idPedido, idStatus)).thenReturn(pedido);
        when(pedidoMapper.toPedidoResponse(pedido)).thenReturn(pedidoResponse);

        // Act
        ResponseEntity<PedidoResponse> resposta = pedidoController.alterarStatusPedido(idPedido, idStatus);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }
}
