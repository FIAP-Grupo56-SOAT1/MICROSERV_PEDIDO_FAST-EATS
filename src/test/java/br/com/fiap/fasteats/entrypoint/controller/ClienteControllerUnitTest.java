package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.ClienteMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.ClienteRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.ClienteResponse;
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

class ClienteControllerUnitTest {
    @Mock
    private ClienteInputPort clienteInputPort;
    @Mock
    private ClienteMapper clienteMapper;
    @InjectMocks
    private ClienteController clienteController;
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
    void criarCliente_DeveRetornarClienteResponse_QuandoCriacaoBemSucedida() {
        // Arrange
        ClienteRequest clienteRequest = new ClienteRequest();
        Cliente cliente = new Cliente();
        ClienteResponse clienteResponse = new ClienteResponse();

        when(clienteMapper.toCliente(clienteRequest)).thenReturn(cliente);
        when(clienteInputPort.criar(cliente)).thenReturn(cliente);
        when(clienteMapper.toClienteResponse(cliente)).thenReturn(clienteResponse);

        // Act
        ResponseEntity<ClienteResponse> resposta = clienteController.criarCliente(clienteRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void consultarCliente_DeveRetornarClienteResponse_QuandoConsultaBemSucedida() {
        // Arrange
        String cpf = "12345678900";
        Cliente cliente = new Cliente();
        ClienteResponse clienteResponse = new ClienteResponse();

        when(clienteInputPort.consultar(cpf)).thenReturn(cliente);
        when(clienteMapper.toClienteResponse(cliente)).thenReturn(clienteResponse);

        // Act
        ResponseEntity<ClienteResponse> resposta = clienteController.consultarCliente(cpf);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void listarClientes_DeveRetornarListaClienteResponse_QuandoListaNaoVazia() {
        // Arrange
        List<Cliente> clientes = Collections.singletonList(new Cliente());
        List<ClienteResponse> clientesResponse = Collections.singletonList(new ClienteResponse());

        when(clienteInputPort.listar()).thenReturn(clientes);
        when(clienteMapper.toClientesResponse(clientes)).thenReturn(clientesResponse);

        // Act
        ResponseEntity<List<ClienteResponse>> resposta = clienteController.listarClientes();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertFalse(resposta.getBody().isEmpty());
    }

    @Test
    void listarClientes_DeveRetornarListaVazia_QuandoListaVazia() {
        // Arrange
        List<Cliente> clientes = Collections.emptyList();

        when(clienteInputPort.listar()).thenReturn(clientes);

        // Act
        ResponseEntity<List<ClienteResponse>> resposta = clienteController.listarClientes();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }

    @Test
    void atualizar_DeveRetornarClienteResponse_QuandoAtualizacaoBemSucedida() {
        // Arrange
        String cpf = "12345678900";
        ClienteRequest clienteRequest = new ClienteRequest();
        Cliente cliente = new Cliente();
        ClienteResponse clienteResponse = new ClienteResponse();

        when(clienteMapper.toCliente(clienteRequest)).thenReturn(cliente);
        when(clienteInputPort.atualizar(cliente)).thenReturn(cliente);
        when(clienteMapper.toClienteResponse(cliente)).thenReturn(clienteResponse);

        // Act
        ResponseEntity<ClienteResponse> resposta = clienteController.atualizar(cpf, clienteRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void deletar_DeveRetornarNoContent_QuandoDelecaoBemSucedida() {
        // Arrange
        String cpf = "12345678900";

        // Act
        ResponseEntity<Void> resposta = clienteController.deletar(cpf);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    }
}