package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.ClienteOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ClienteNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.exception.ValidarClienteException;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.usecase.impl.ClienteUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ClienteUseCaseUnitTest")
class ClienteUseCaseUnitTest {
    @Mock
    private ClienteOutputPort clienteOutputPort;
    @InjectMocks
    private ClienteUseCase clienteUseCase;
    AutoCloseable openMocks;
    private static final String CPF_VALIDO = "84000655493";
    private static final String CPF_INVALIDO = "817334340";

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve criar um novo cliente")
    void testCriarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(CPF_VALIDO);
        cliente.setEmail("teste@teste.com");
        cliente.setPrimeiroNome("Fiap");
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);

        when(clienteOutputPort.salvarCliente(cliente)).thenReturn(cliente);

        Cliente resultado = clienteUseCase.criar(cliente);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteOutputPort, times(1)).salvarCliente(cliente);
    }

    @Test
    @DisplayName("Deve apresentar erro ao criar um cliente que já existe")
    void testCriarClienteQueJaExiste() {
        Cliente cliente = new Cliente();
        cliente.setCpf(CPF_VALIDO);
        cliente.setEmail("teste@teste.com");
        cliente.setPrimeiroNome("Fiap");
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);

        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(cliente));

        assertThrows(RegraNegocioException.class, () -> clienteUseCase.criar(cliente));
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Test
    @DisplayName("Deve apresentar erro ao tentar criar um cliente e não informar email")
    void testCriarClienteSemEmail() {
        Cliente cliente = new Cliente();
        cliente.setCpf(CPF_VALIDO);
        cliente.setPrimeiroNome("Fiap");
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);

        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.empty());

        assertThrows(ValidarClienteException.class, () -> clienteUseCase.criar(cliente));
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Test
    @DisplayName("Deve consultar um cliente existente")
    void testConsultarClienteExistente() {
        Cliente cliente = new Cliente();

        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteUseCase.consultar(CPF_VALIDO);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar um cliente com CPF inválido")
    void testConsultarClienteComCpfInvalido() {
        assertThrows(RegraNegocioException.class, () -> clienteUseCase.consultar(CPF_INVALIDO));
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar um cliente inexistente")
    void testConsultarClienteInexistente() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFound.class, () -> clienteUseCase.consultar(CPF_VALIDO));
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Test
    @DisplayName("Deve listar os clientes")
    void testListarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente());

        when(clienteOutputPort.listar()).thenReturn(Optional.of(clientes));

        List<Cliente> resultado = clienteUseCase.listar();

        assertNotNull(resultado);
        assertEquals(clientes.size(), resultado.size());
        assertEquals(clientes, resultado);
        verify(clienteOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Deve atualizar um cliente")
    void testAtualizarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(CPF_VALIDO);
        cliente.setEmail("teste@teste.com");
        cliente.setPrimeiroNome("Fiap");
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);

        when(clienteOutputPort.salvarCliente(cliente)).thenReturn(cliente);

        Cliente resultado = clienteUseCase.atualizar(cliente);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteOutputPort, times(1)).salvarCliente(cliente);
    }

    @Test
    @DisplayName("Deve verificar se um cliente existe")
    void testClienteExiste() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(new Cliente()));

        boolean resultado = clienteUseCase.clienteExiste(CPF_VALIDO);

        assertTrue(resultado);
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Test
    @DisplayName("Deve verificar que um cliente não existe")
    void testClienteNaoExiste() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.empty());

        boolean resultado = clienteUseCase.clienteExiste(CPF_VALIDO);

        assertFalse(resultado);
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar um cliente com CPF vazio")
    void testValidarClienteComCpfVazio() {
        Cliente cliente = new Cliente();
        cliente.setCpf("");
        cliente.setEmail("teste@teste.com");
        cliente.setPrimeiroNome("Fiap");
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);

        assertThrows(IllegalArgumentException.class, () -> clienteUseCase.validarCliente(cliente));
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar um cliente com CPF inválido")
    void testValidarClienteComCpfInvalido() {
        Cliente cliente = new Cliente();
        cliente.setCpf(CPF_INVALIDO);
        cliente.setEmail("teste@teste.com");
        cliente.setPrimeiroNome("Fiap");
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);

        assertThrows(RegraNegocioException.class, () -> clienteUseCase.validarCliente(cliente));
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    void testDeletarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(CPF_INVALIDO);
        cliente.setEmail("teste@teste.com");
        cliente.setPrimeiroNome("Fiap");
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);

        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(cliente));

        clienteUseCase.deletar(CPF_VALIDO);
        verify(clienteOutputPort, times(1)).deletar(CPF_VALIDO);
    }
}
