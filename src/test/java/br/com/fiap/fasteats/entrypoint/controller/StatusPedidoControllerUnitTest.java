package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.StatusPedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.StatusPedidoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.StatusPedidoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StatusPedidoControllerUnitTest {
    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;
    @Mock
    private StatusPedidoMapper statusPedidoMapper;
    @InjectMocks
    private StatusPedidoController statusPedidoController;
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
    void criarStatusPedido_DeveRetornarStatusPedidoResponse_QuandoCriacaoBemSucedida() {
        // Arrange
        StatusPedidoRequest statusPedidoRequest = new StatusPedidoRequest();
        StatusPedido statusPedido = new StatusPedido();
        StatusPedido statusPedidoCriado = new StatusPedido();
        StatusPedidoResponse statusPedidoResponse = new StatusPedidoResponse();

        when(statusPedidoMapper.toStatusPedido(statusPedidoRequest)).thenReturn(statusPedido);
        when(statusPedidoInputPort.criar(statusPedido)).thenReturn(statusPedidoCriado);
        when(statusPedidoMapper.toStatusPedidoResponse(statusPedidoCriado)).thenReturn(statusPedidoResponse);

        // Act
        ResponseEntity<StatusPedidoResponse> resposta = statusPedidoController.criarStatusPedido(statusPedidoRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void atualizarStatusPedido_DeveRetornarStatusPedidoResponse_QuandoAtualizacaoBemSucedida() {
        // Arrange
        Long id = 1L;
        StatusPedidoRequest statusPedidoRequest = new StatusPedidoRequest();
        StatusPedido statusPedido = new StatusPedido();
        StatusPedido statusPedidoAtualizado = new StatusPedido();
        StatusPedidoResponse statusPedidoResponse = new StatusPedidoResponse();

        when(statusPedidoMapper.toStatusPedido(statusPedidoRequest)).thenReturn(statusPedido);
        when(statusPedidoInputPort.atualizar(statusPedido)).thenReturn(statusPedidoAtualizado);
        when(statusPedidoMapper.toStatusPedidoResponse(statusPedidoAtualizado)).thenReturn(statusPedidoResponse);

        // Act
        ResponseEntity<StatusPedidoResponse> resposta = statusPedidoController.atualizarStatusPedido(id, statusPedidoRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void deletarStatusPedido_DeveRetornarNoContent_QuandoDelecaoBemSucedida() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> resposta = statusPedidoController.deletarStatusPedido(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    }

    @Test
    void consultarStatusPedido_DeveRetornarStatusPedidoResponse_QuandoConsultaBemSucedida() {
        // Arrange
        Long id = 1L;
        StatusPedido statusPedido = new StatusPedido();
        StatusPedidoResponse statusPedidoResponse = new StatusPedidoResponse();

        when(statusPedidoInputPort.consultar(id)).thenReturn(statusPedido);
        when(statusPedidoMapper.toStatusPedidoResponse(statusPedido)).thenReturn(statusPedidoResponse);

        // Act
        ResponseEntity<StatusPedidoResponse> resposta = statusPedidoController.consultarStatusPedido(id);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void consultarStatusPedidoPorNome_DeveRetornarStatusPedidoResponse_QuandoConsultaPorNomeBemSucedida() {
        // Arrange
        String nome = "Em andamento";
        StatusPedido statusPedido = new StatusPedido();
        StatusPedidoResponse statusPedidoResponse = new StatusPedidoResponse();

        when(statusPedidoInputPort.consultarPorNome(nome)).thenReturn(statusPedido);
        when(statusPedidoMapper.toStatusPedidoResponse(statusPedido)).thenReturn(statusPedidoResponse);

        // Act
        ResponseEntity<StatusPedidoResponse> resposta = statusPedidoController.consultarStatusPedidoPorNome(nome);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void listarStatusPedidos_DeveRetornarListaStatusPedidoResponse_QuandoListaNaoVazia() {
        // Arrange
        List<StatusPedido> statusPedidos = List.of(new StatusPedido());
        List<StatusPedidoResponse> statusPedidoResponseList = List.of(new StatusPedidoResponse());

        when(statusPedidoInputPort.listar()).thenReturn(statusPedidos);
        when(statusPedidoMapper.toStatusPedidoResponseList(statusPedidos)).thenReturn(statusPedidoResponseList);

        // Act
        ResponseEntity<List<StatusPedidoResponse>> resposta = statusPedidoController.listarStatusPedidos();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertFalse(resposta.getBody().isEmpty());
    }

    @Test
    void listarStatusPedidos_DeveRetornarListaVazia_QuandoListaVazia() {
        // Arrange
        List<StatusPedido> statusPedidos = List.of();

        when(statusPedidoInputPort.listar()).thenReturn(statusPedidos);

        // Act
        ResponseEntity<List<StatusPedidoResponse>> resposta = statusPedidoController.listarStatusPedidos();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }
}
