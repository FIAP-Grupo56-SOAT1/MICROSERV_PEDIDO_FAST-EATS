package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AndamentoPedidoInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.GerenciarPedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.response.GerenciarPedidoResponse;
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
import static org.mockito.Mockito.when;

class GerenciarPedidosControllerUnitTest {
    @Mock
    private AndamentoPedidoInputPort andamentoPedidoInputPort;
    @Mock
    private GerenciarPedidoMapper gerenciarPedidoMapper;
    @InjectMocks
    private GerenciarPedidosController gerenciarPedidosController;
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
    void consultarDetalhePedido_DeveRetornarGerenciarPedidoResponse_QuandoConsultaBemSucedida() {
        // Arrange
        Long id = 1L;
        Pedido pedido = new Pedido();
        GerenciarPedidoResponse gerenciarPedidoResponse = new GerenciarPedidoResponse();

        when(andamentoPedidoInputPort.consultarAndamentoPedido(id)).thenReturn(pedido);
        when(gerenciarPedidoMapper.toGerenciarPedidoResponse(pedido)).thenReturn(gerenciarPedidoResponse);

        // Act
        ResponseEntity<GerenciarPedidoResponse> resposta = gerenciarPedidosController.consultarDetalhePedido(id);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void consultarPedidosEmAndamento_DeveRetornarListaGerenciarPedidoResponse_QuandoConsultaBemSucedida() {
        // Arrange
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        List<GerenciarPedidoResponse> gerenciarPedidoResponses = Collections.singletonList(new GerenciarPedidoResponse());

        when(andamentoPedidoInputPort.consultarPedidosEmAndamento()).thenReturn(pedidos);
        when(gerenciarPedidoMapper.toGerenciarPedidosResponse(pedidos)).thenReturn(gerenciarPedidoResponses);

        // Act
        ResponseEntity<List<GerenciarPedidoResponse>> resposta = gerenciarPedidosController.consultarPedidosEmAndamento();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertFalse(resposta.getBody().isEmpty());
    }

    @Test
    void consultarPedidosEmAndamento_DeveRetornarListaVazia_QuandoListaVazia() {
        // Arrange
        List<Pedido> pedidos = Collections.emptyList();

        when(andamentoPedidoInputPort.consultarPedidosEmAndamento()).thenReturn(pedidos);

        // Act
        ResponseEntity<List<GerenciarPedidoResponse>> resposta = gerenciarPedidosController.consultarPedidosEmAndamento();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }
}
