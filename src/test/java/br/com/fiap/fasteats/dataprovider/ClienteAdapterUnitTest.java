package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.dataprovider.repository.ClienteRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.ClienteEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.ClienteEntityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClienteAdapterUnitTest {
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteEntityMapper clienteEntityMapper;
    @InjectMocks
    private ClienteAdapter clienteAdapter;
    AutoCloseable openMocks;
    private final String CPF_VALIDO = "12345678909";

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void salvarCliente() {
        // Arrange
        Cliente cliente = getCliente(CPF_VALIDO);
        ClienteEntity clienteEntity = getClienteEntity(CPF_VALIDO);

        when(clienteEntityMapper.toClienteEntity(cliente)).thenReturn(clienteEntity);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(clienteEntityMapper.toCliente(clienteEntity)).thenReturn(cliente);

        // Act
        Cliente clienteSalvo = clienteAdapter.salvarCliente(cliente);

        // Assert
        assertEquals(cliente.getCpf(), clienteSalvo.getCpf());
        assertEquals(cliente, toCliente(clienteEntity));
        verify(clienteEntityMapper).toClienteEntity(cliente);
        verify(clienteRepository).save(clienteEntity);
        verify(clienteEntityMapper).toCliente(clienteEntity);
    }

    @Test
    void consultarCliente() {
        // Arrange
        Cliente cliente = getCliente(CPF_VALIDO);
        ClienteEntity clienteEntity = getClienteEntity(CPF_VALIDO);
        String nomeCompleto = cliente.getPrimeiroNome() + " " + cliente.getUltimoNome();


        //when(clienteRepository.findByCpf(CPF_VALIDO)).thenReturn(java.util.Optional.of(clienteEntity));
        when(clienteEntityMapper.toCliente(clienteEntity)).thenReturn(cliente);

        // Act
        Optional<Cliente> clienteConsultado = clienteAdapter.consultarCliente(CPF_VALIDO);

        // Assert
        assertTrue(clienteConsultado.isPresent());
        assertEquals(cliente, toCliente(clienteEntity));
        assertEquals(cliente.getCpf(), clienteConsultado.get().getCpf());
        assertEquals(nomeCompleto , clienteEntity.getNome());
        verify(clienteRepository).findByCpf(CPF_VALIDO);
        verify(clienteEntityMapper).toCliente(clienteEntity);
    }

    @Test
    void listar() {
        // Arrange
        List<ClienteEntity> clientesEntity = List.of(getClienteEntity(CPF_VALIDO), getClienteEntity("98765432109"));
        List<Cliente> clientes = List.of(getCliente(CPF_VALIDO), getCliente("98765432109"));

        when(clienteRepository.findAll()).thenReturn(clientesEntity);
        when(clienteEntityMapper.toCliente(clientesEntity.get(0))).thenReturn(clientes.get(0));
        when(clienteEntityMapper.toCliente(clientesEntity.get(1))).thenReturn(clientes.get(1));

        // Act
        Optional<List<Cliente>> clientesListados = clienteAdapter.listar();

        // Assert
        assertTrue(clientesListados.isPresent());
        assertEquals(clientes.get(0).getCpf(), clientesListados.get().get(0).getCpf());
        assertEquals(clientes.get(1).getCpf(), clientesListados.get().get(1).getCpf());
        verify(clienteRepository).findAll();
        verify(clienteEntityMapper).toCliente(clientesEntity.get(0));
    }

    @Test
    void deletar() {
        // Act
        clienteAdapter.deletar(CPF_VALIDO);

        // Assert
        //verify(clienteRepository).deleteById(CPF_VALIDO);
    }

    private Cliente getCliente(String cpf) {
        long clienteId = 1L;
        return new Cliente(clienteId,cpf, "TESTE", "TESTE", "teste@teste.com", true);
    }

    private ClienteEntity getClienteEntity(String cpf) {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setCpf(cpf);
        clienteEntity.setPrimeiroNome("TESTE");
        clienteEntity.setUltimoNome("TESTE");
        clienteEntity.setEmail("teste@teste.com");
        clienteEntity.setAtivo(true);
        return clienteEntity;
    }

    public Cliente toCliente(ClienteEntity clienteEntity) {
        Cliente cliente = new Cliente();
        cliente.setCpf(clienteEntity.getCpf());
        cliente.setPrimeiroNome(clienteEntity.getPrimeiroNome());
        cliente.setUltimoNome(clienteEntity.getUltimoNome());
        cliente.setEmail(clienteEntity.getEmail());
        cliente.setAtivo(clienteEntity.getAtivo());
        return cliente;
    }
}