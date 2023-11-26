//package br.com.fiap.fasteats.core.usecase.impl.integration;
//
//import br.com.fiap.fasteats.core.domain.exception.ClienteNotFound;
//import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
//import br.com.fiap.fasteats.core.domain.model.Cliente;
//import br.com.fiap.fasteats.core.usecase.impl.ClienteUseCase;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@DisplayName("ClienteUseCaseUnitTest")
//class ClienteUseCaseIntegrationTest {
//    @Autowired
//    private ClienteUseCase clienteUseCase;
//
//    private final String CPF_VALIDO = "84000655493";
//    private final String CPF_INVALIDO = "817334340";
//    private final Cliente CLIENTE_BASE = new Cliente(CPF_VALIDO, "Fiap", "Soat1", "teste@teste.com", true);
//
//    @Test
//    @Transactional
//    @DisplayName("Deve criar um novo cliente")
//    void testCriarCliente() {
//        Cliente cliente = CLIENTE_BASE;
//
//        Cliente resultado = clienteUseCase.criar(cliente);
//
//        assertNotNull(resultado);
//        assertEquals(cliente, resultado);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve consultar um cliente existente")
//    void testConsultarClienteExistente() {
//        Cliente cliente = clienteUseCase.criar(CLIENTE_BASE);
//
//        Cliente resultado = clienteUseCase.consultar(CPF_VALIDO);
//
//        assertNotNull(resultado);
//        assertEquals(cliente, resultado);
//    }
//
//    @Test
//    @DisplayName("Deve lançar exceção ao consultar um cliente com CPF inválido")
//    void testConsultarClienteComCpfInvalido() {
//        assertThrows(RegraNegocioException.class, () -> clienteUseCase.consultar(CPF_INVALIDO));
//    }
//
//    @Test
//    @DisplayName("Deve lançar exceção ao consultar um cliente inexistente")
//    void testConsultarClienteInexistente() {
//        assertThrows(ClienteNotFound.class, () -> clienteUseCase.consultar(CPF_VALIDO));
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve listar os clientes")
//    void testListarClientes() {
//        List<Cliente> clientes = new ArrayList<>();
//        clientes.add(clienteUseCase.criar(CLIENTE_BASE));
//
//        List<Cliente> resultado = clienteUseCase.listar();
//
//        assertNotNull(resultado);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve atualizar um cliente")
//    void testAtualizarCliente() {
//        Cliente cliente = clienteUseCase.criar(CLIENTE_BASE);
//        cliente.setPrimeiroNome("Fiap2");
//        cliente.setUltimoNome("Soat2");
//
//        Cliente resultado = clienteUseCase.atualizar(cliente);
//
//        assertNotNull(resultado);
//        assertEquals(cliente, resultado);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deve verificar se um cliente existe")
//    void testClienteExiste() {
//        clienteUseCase.criar(CLIENTE_BASE);
//
//        boolean resultado = clienteUseCase.clienteExiste(CPF_VALIDO);
//
//        assertTrue(resultado);
//    }
//
//    @Test
//    @DisplayName("Deve verificar que um cliente não existe")
//    void testClienteNaoExiste() {
//        boolean resultado = clienteUseCase.clienteExiste(CPF_VALIDO);
//
//        assertFalse(resultado);
//    }
//
//    @Test
//    @DisplayName("Deve lançar exceção ao validar um cliente com CPF vazio")
//    void testValidarClienteComCpfVazio() {
//        Cliente cliente = new Cliente();
//        cliente.setCpf("");
//        cliente.setEmail("teste@teste.com");
//        cliente.setPrimeiroNome("Fiap");
//        cliente.setUltimoNome("Soat1");
//        cliente.setAtivo(true);
//
//        assertThrows(IllegalArgumentException.class, () -> clienteUseCase.validarCliente(cliente));
//    }
//
//    @Test
//    @DisplayName("Deve lançar exceção ao validar um cliente com CPF inválido")
//    void testValidarClienteComCpfInvalido() {
//        Cliente cliente = new Cliente();
//        cliente.setCpf(CPF_INVALIDO);
//        cliente.setEmail("teste@teste.com");
//        cliente.setPrimeiroNome("Fiap");
//        cliente.setUltimoNome("Soat1");
//        cliente.setAtivo(true);
//
//        assertThrows(RegraNegocioException.class, () -> clienteUseCase.validarCliente(cliente));
//    }
//}
