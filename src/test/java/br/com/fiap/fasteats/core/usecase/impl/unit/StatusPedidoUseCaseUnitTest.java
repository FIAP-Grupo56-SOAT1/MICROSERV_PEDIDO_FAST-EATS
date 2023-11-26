package br.com.fiap.fasteats.core.usecase.impl.unit;

import br.com.fiap.fasteats.core.domain.exception.StatusPedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.dataprovider.StatusPedidoOutputPort;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("StatusPedidoUseCaseUnitTest")
class StatusPedidoUseCaseUnitTest {

    private StatusPedidoUseCase statusPedidoUseCase;

    private static final Long ID_EXISTENTE = 1L;
    private static final Long ID_INEXISTENTE = 2L;
    private static final String NOME_EXISTENTE = "RECEBIDO";
    private static final String NOME_INEXISTENTE = "CANCELADO";

    @Mock
    private StatusPedidoOutputPort statusPedidoOutputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statusPedidoUseCase = new StatusPedidoUseCase(statusPedidoOutputPort);
    }

    @Test
    @DisplayName("Deve criar um novo status de pedido")
    void testCriarStatusPedido() {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(ID_EXISTENTE);
        statusPedido.setNome("Aprovado");
        statusPedido.setAtivo(true);

        when(statusPedidoOutputPort.criar(statusPedido)).thenReturn(statusPedido);

        StatusPedido resultado = statusPedidoUseCase.criar(statusPedido);

        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals("APROVADO", resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).criar(statusPedido);
    }

    @Test
    @DisplayName("Deve consultar um status de pedido existente por ID")
    void testConsultarStatusPedidoExistentePorId() {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(ID_EXISTENTE);
        statusPedido.setNome("APROVADO");
        statusPedido.setAtivo(true);

        when(statusPedidoOutputPort.consultar(ID_EXISTENTE)).thenReturn(Optional.of(statusPedido));

        StatusPedido resultado = statusPedidoUseCase.consultar(ID_EXISTENTE);

        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals("APROVADO", resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).consultar(ID_EXISTENTE);
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar um status de pedido inexistente por ID")
    void testConsultarStatusPedidoInexistentePorId() {
        when(statusPedidoOutputPort.consultar(ID_INEXISTENTE)).thenReturn(Optional.empty());

        assertThrows(StatusPedidoNotFound.class, () -> statusPedidoUseCase.consultar(ID_INEXISTENTE));
        verify(statusPedidoOutputPort, times(1)).consultar(ID_INEXISTENTE);
    }

    @Test
    @DisplayName("Deve atualizar um status de pedido")
    void testAtualizarStatusPedido() {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(ID_EXISTENTE);
        statusPedido.setNome("Aprovado");
        statusPedido.setAtivo(true);

        when(statusPedidoOutputPort.atualizar(statusPedido)).thenReturn(statusPedido);

        StatusPedido resultado = statusPedidoUseCase.atualizar(statusPedido);

        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals("APROVADO", resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).atualizar(statusPedido);
    }

    @Test
    @DisplayName("Deve deletar um status de pedido por ID")
    void testDeletarStatusPedidoPorId() {
        statusPedidoUseCase.deletar(ID_EXISTENTE);
        verify(statusPedidoOutputPort, times(1)).deletar(ID_EXISTENTE);
    }

    @Test
    @DisplayName("Deve listar os status de pedido")
    void testListarStatusPedido() {
        List<StatusPedido> statusPedidos = new ArrayList<>();
        statusPedidos.add(new StatusPedido());

        when(statusPedidoOutputPort.listar()).thenReturn(Optional.of(statusPedidos));

        List<StatusPedido> resultado = statusPedidoUseCase.listar();

        assertNotNull(resultado);
        assertEquals(statusPedidos.size(), resultado.size());
        assertEquals(statusPedidos, resultado);
        verify(statusPedidoOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Deve lançar exceção ao listar os status de pedido quando não há registros")
    void testListarStatusPedidoSemRegistros() {
        when(statusPedidoOutputPort.listar()).thenReturn(Optional.empty());

        assertThrows(StatusPedidoNotFound.class, () -> statusPedidoUseCase.listar());
        verify(statusPedidoOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Deve consultar um status de pedido existente por nome")
    void testConsultarStatusPedidoExistentePorNome() {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(ID_EXISTENTE);
        statusPedido.setNome(NOME_EXISTENTE);
        statusPedido.setAtivo(true);

        when(statusPedidoOutputPort.consultarPorNome(NOME_EXISTENTE)).thenReturn(Optional.of(statusPedido));

        StatusPedido resultado = statusPedidoUseCase.consultarPorNome(NOME_EXISTENTE);

        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals(NOME_EXISTENTE.toUpperCase(), resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).consultarPorNome(NOME_EXISTENTE);
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar um status de pedido inexistente por nome")
    void testConsultarStatusPedidoInexistentePorNome() {
        when(statusPedidoOutputPort.consultarPorNome(NOME_INEXISTENTE)).thenReturn(Optional.empty());

        assertThrows(StatusPedidoNotFound.class, () -> statusPedidoUseCase.consultarPorNome(NOME_INEXISTENTE));
        verify(statusPedidoOutputPort, times(1)).consultarPorNome(NOME_INEXISTENTE);
    }
}
