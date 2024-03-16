package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CRIADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PedidoUseCaseUnitTest")
class PedidoUseCaseUnitTest {
    private PedidoInputPort pedidoUseCase;

    @Mock
    private PedidoOutputPort pedidoOutputPort;

    @Mock
    private ClienteInputPort clienteInputPort;

    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;

    @Mock
    private PagamentoOutputPort pagamentoOutputPort;

    @Mock
    private PedidoValidator pedidoValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoUseCase = new PedidoUseCase(pedidoOutputPort,
                clienteInputPort,
                statusPedidoInputPort,
                pagamentoOutputPort,
                pedidoValidator);
    }

    @Test
    @DisplayName("Deve criar um pedido com cliente identificado")
    void testeCriarPedidoComClienteIdentificado() {
        Long idPedido = 1L;
        Long idStatusPedidoCriado = 1L;
        LocalDateTime dataHoraCriado = LocalDateTime.now();

        Pedido pedido = new Pedido();
        pedido.setIdentificaCliente(true);
        pedido.setCliente(new Cliente());
        pedido.getCliente().setCpf("12345678900");

        Pedido pedidoComCliente = new Pedido();
        pedidoComCliente.setId(idPedido);
        pedidoComCliente.setDataHoraCriado(dataHoraCriado);
        pedidoComCliente.setStatusPedido(idStatusPedidoCriado);

        StatusPedido statusPedidoCriado = criarStatusPedido(idStatusPedidoCriado, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultarPorNome(STATUS_PEDIDO_CRIADO)).thenReturn(statusPedidoCriado);
        when(clienteInputPort.consultar("12345678900")).thenReturn(null);
        when(clienteInputPort.criar(any(Cliente.class))).thenReturn(new Cliente());
        when(pedidoOutputPort.salvarPedido(any(Pedido.class))).thenReturn(pedidoComCliente);

        Pedido resultado = pedidoUseCase.criar(pedido);

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedidoCriado, resultado.getStatusPedido());
        assertNotNull(resultado.getDataHoraCriado());
        verify(pedidoOutputPort, times(1)).salvarPedido(any(Pedido.class));
        verify(clienteInputPort, times(1)).criar(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve criar um pedido com cliente identificado que já existe")
    void testeCriarPedidoComClienteIdentificadoQueJaExiste() {
        Long idPedido = 1L;
        String cpf = "12345678909";
        Long idStatusPedidoCriado = 1L;
        LocalDateTime dataHoraCriado = LocalDateTime.now();

        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setPrimeiroNome("Cliente");
        cliente.setUltimoNome("Teste");
        cliente.setEmail("teste@teste.com");
        cliente.setAtivo(true);


        Pedido pedido = new Pedido();
        pedido.setIdentificaCliente(true);
        pedido.setCliente(cliente);

        Pedido pedidoComCliente = new Pedido();
        pedidoComCliente.setId(idPedido);
        pedidoComCliente.setDataHoraCriado(dataHoraCriado);
        pedidoComCliente.setStatusPedido(idStatusPedidoCriado);

        StatusPedido statusPedidoCriado = criarStatusPedido(idStatusPedidoCriado, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultarPorNome(STATUS_PEDIDO_CRIADO)).thenReturn(statusPedidoCriado);
        when(clienteInputPort.clienteExiste(cpf)).thenReturn(true);
        when(clienteInputPort.consultar(cpf)).thenReturn(cliente);
        when(pedidoOutputPort.salvarPedido(any(Pedido.class))).thenReturn(pedidoComCliente);

        Pedido resultado = pedidoUseCase.criar(pedido);

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedidoCriado, resultado.getStatusPedido());
        assertNotNull(resultado.getDataHoraCriado());
        verify(pedidoOutputPort, times(1)).salvarPedido(any(Pedido.class));
        verify(clienteInputPort, times(0)).criar(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve criar um pedido sem identificação do cliente")
    void testeCriarPedidoSemIdentificacaoCliente() {
        Long idPedido = 1L;
        Long idStatusPedidoCriado = 1L;
        LocalDateTime dataHoraCriado = LocalDateTime.now();

        Pedido pedido = new Pedido();
        pedido.setIdentificaCliente(false);

        Pedido pedidoSemCliente = new Pedido();
        pedidoSemCliente.setId(idPedido);
        pedidoSemCliente.setDataHoraCriado(dataHoraCriado);
        pedidoSemCliente.setStatusPedido(idStatusPedidoCriado);

        StatusPedido statusPedidoCriado = criarStatusPedido(idStatusPedidoCriado, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultarPorNome(STATUS_PEDIDO_CRIADO)).thenReturn(statusPedidoCriado);
        when(pedidoOutputPort.salvarPedido(any(Pedido.class))).thenReturn(pedidoSemCliente);

        Pedido resultado = pedidoUseCase.criar(pedido);

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedidoCriado, resultado.getStatusPedido());
        assertNotNull(resultado.getDataHoraCriado());
        verify(pedidoOutputPort, times(1)).salvarPedido(any(Pedido.class));
        verify(clienteInputPort, never()).criar(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve consultar um pedido pelo ID")
    void testeConsultarPedido() {
        Long idPedido = 1L;
        Long idStatusPedido = 2L;

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);

        when(pedidoOutputPort.consultarPedido(idPedido)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoUseCase.consultar(idPedido);

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).consultarPedido(idPedido);
    }

    @Test
    @DisplayName("Deve lançar PedidoNotFound ao consultar um pedido inexistente")
    void testeConsultarPedidoInexistente() {
        Long idPedido = 1L;

        when(pedidoOutputPort.consultarPedido(idPedido)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFound.class, () -> pedidoUseCase.consultar(idPedido));
        verify(pedidoOutputPort, times(1)).consultarPedido(idPedido);
    }

    @Test
    @DisplayName("Deve listar todos os pedidos")
    void testeListarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido() {
            {
                setId(1L);
            }
        });
        pedidos.add(new Pedido() {
            {
                setId(2L);
            }
        });

        when(pedidoOutputPort.listar()).thenReturn(pedidos);

        List<Pedido> resultado = pedidoUseCase.listar();

        assertNotNull(resultado);
        assertEquals(pedidos.size(), resultado.size());
        verify(pedidoOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Deve atualizar um pedido")
    void testeAtualizarPedido() {
        Long idPedido = 1L;
        Long idStatusPedido = 2L;
        List<StatusPedido> listaStatus = new ArrayList<>();
        listaStatus.add(new StatusPedido(1L, "CRIADO", true));
        listaStatus.add(new StatusPedido(2L, "AGUARDANDO_PAGAMENTO", true));
        listaStatus.add(new StatusPedido(3L, "PAGO", true));
        listaStatus.add(new StatusPedido(4L, "RECEBIDO", true));
        listaStatus.add(new StatusPedido(5L, "EM_PREPARO", true));
        listaStatus.add(new StatusPedido(6L, "PRONTO", true));
        listaStatus.add(new StatusPedido(7L, "FINALIZADO", true));

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(pedido.getId())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.listar()).thenReturn(listaStatus);

        Pedido resultado = pedidoUseCase.atualizar(pedido);

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve deletar um pedido")
    void testeDeletarPedido() {
        Long idPedido = 1L;

        when(pedidoOutputPort.consultarPedido(idPedido)).thenReturn(Optional.of(new Pedido() {
            {
                setId(idPedido);
            }
        }));
        doNothing().when(pedidoOutputPort).deletar(idPedido);

        assertDoesNotThrow(() -> pedidoUseCase.deletar(idPedido));
        verify(pedidoOutputPort, times(1)).consultarPedido(idPedido);
        verify(pedidoOutputPort, times(1)).deletar(idPedido);
    }

    @Test
    @DisplayName("Deve lançar PedidoNotFound ao deletar um pedido inexistente")
    void testeDeletarPedidoInexistente() {
        Long idPedido = 1L;

        when(pedidoOutputPort.consultarPedido(idPedido)).thenReturn(Optional.empty());

        assertThrows(PedidoNotFound.class, () -> pedidoUseCase.deletar(idPedido));
        verify(pedidoOutputPort, times(1)).consultarPedido(idPedido);
        verify(pedidoOutputPort, never()).deletar(idPedido);
    }

    @Test
    @DisplayName("Deve atualizar o valor do pedido")
    void testeAtualizarValorPedido() {
        // Arrange
        Pedido pedido = new Pedido();
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setValor(20.0);
        produtoPedido.setQuantidade(2);
        pedido.setValor(100.0);
        pedido.setProdutos(List.of(produtoPedido));

        // Act
        pedidoUseCase.atualizarValorPedido(pedido);

        // Assert
        assertEquals(40.0, pedido.getValor());
    }

    private StatusPedido criarStatusPedido(Long id, String nome) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(id);
        statusPedido.setNome(nome);
        return statusPedido;
    }
}
