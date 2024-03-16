package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.ConcluirPedidoPagoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PedidoUseCaseUnitTest")
class AlterarPedidoStatusUseCaseUnitTest {
    @Mock
    private AlterarPedidoStatusValidator alterarPedidoStatusValidator;
    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;
    @Mock
    private PedidoOutputPort pedidoOutputPort;
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private ConcluirPedidoPagoOutputPort concluirPedidoPagoOutputPort;
    @InjectMocks
    private AlterarPedidoStatusUseCase alterarPedidoStatusUseCase;
    private AutoCloseable openMocks;


    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para aguardandoPagamento")
    void testeAguardandoPagamento() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 2L;
        StatusPedido statusPedido = criarStatusPedido(2L, STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(alterarPedidoStatusValidator).validarAguardandoPagamento(anyLong());
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoInputPort.formatarPedido(any())).thenReturn(pedido);

        //Act
        Pedido resultado = alterarPedidoStatusUseCase.aguardandoPagamento(anyLong());

        // Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para pago")
    void testePago() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 3L;
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, STATUS_PEDIDO_PAGO);
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(alterarPedidoStatusValidator).validarPago(anyLong());
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(concluirPedidoPagoOutputPort.concluirPagamento(any())).thenReturn(pedido);
        when(pedidoInputPort.formatarPedido(pedido)).thenReturn(pedido);

        //Act
        Pedido resultado = alterarPedidoStatusUseCase.pago(idPedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(concluirPedidoPagoOutputPort, times(1)).concluirPagamento(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para recebido")
    void testeRecebido() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 4L;
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, STATUS_PEDIDO_RECEBIDO);
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(alterarPedidoStatusValidator).validarRecebido(anyLong());
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoInputPort.formatarPedido(any())).thenReturn(pedido);

        //Act
        Pedido resultado = alterarPedidoStatusUseCase.recebido(idPedido);

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para emPreparo")
    void testeEmPreparo() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 5L;
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, STATUS_PEDIDO_EM_PREPARO);
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(alterarPedidoStatusValidator).validarEmPreparo(anyLong());
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoInputPort.formatarPedido(any())).thenReturn(pedido);

        //Act
        Pedido resultado = alterarPedidoStatusUseCase.emPreparo(idPedido);

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para pronto")
    void testePronto() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 6L;
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, STATUS_PEDIDO_PRONTO);
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(alterarPedidoStatusValidator).validarPronto(anyLong());
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoInputPort.formatarPedido(any())).thenReturn(pedido);

        //Act
        Pedido resultado = alterarPedidoStatusUseCase.pronto(idPedido);

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para finalizado")
    void testeFinalizado() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 7L;
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, STATUS_PEDIDO_FINALIZADO);
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(alterarPedidoStatusValidator).validarFinalizado(anyLong());
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoInputPort.formatarPedido(any())).thenReturn(pedido);

        //Act
        Pedido resultado = alterarPedidoStatusUseCase.finalizado(idPedido);

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para cancelado")
    void testeCancelado() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 8L;
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, STATUS_PEDIDO_CANCELADO);
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(alterarPedidoStatusValidator).validarCancelado(anyLong());
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoInputPort.formatarPedido(any())).thenReturn(pedido);

        //Act
        Pedido resultado = alterarPedidoStatusUseCase.cancelado(idPedido);

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para aguardando pagamento")
    void testeAtualizarStatusPedido() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 2L;
        Long idStatusPedidoCriado = 1L;
        Pedido pedido = getPedido(idPedido, idStatusPedidoCriado);
        List<String> statusPedidoPossiveis = List.of(
                STATUS_PEDIDO_AGUARDANDO_PAGAMENTO,
                STATUS_PEDIDO_PAGO,
                STATUS_PEDIDO_RECEBIDO,
                STATUS_PEDIDO_EM_PREPARO,
                STATUS_PEDIDO_PRONTO,
                STATUS_PEDIDO_FINALIZADO,
                STATUS_PEDIDO_CANCELADO);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));

        //Act
        for(String nomeStatusPedido : statusPedidoPossiveis) {
            StatusPedido statusPedido = criarStatusPedido(idStatusPedido, nomeStatusPedido);

            when(statusPedidoInputPort.consultar(idStatusPedido)).thenReturn(statusPedido);
            when(statusPedidoInputPort.consultarPorNome(nomeStatusPedido)).thenReturn(statusPedido);

            alterarPedidoStatusUseCase.atualizarStatusPedido(idPedido, idStatusPedido);
        }

        //Assert
        verify(statusPedidoInputPort, times(7)).consultarPorNome(anyString());
        verify(pedidoOutputPort, times(6)).salvarPedido(any(Pedido.class));
        verify(concluirPedidoPagoOutputPort, times(1)).concluirPagamento(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve apresentar erro ao tentar alterar o status de um pedido para um status inválido")
    void testeAtualizarStatusPedidoParaStatusInvalido() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 2L;
        String statusPedidoInvalido = "TESTE";
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, statusPedidoInvalido);

        when(statusPedidoInputPort.consultar(idStatusPedido)).thenReturn(statusPedido);

        //Act & Assert
        assertThrows(RuntimeException.class, () -> alterarPedidoStatusUseCase.atualizarStatusPedido(idPedido, idStatusPedido));
        verify(statusPedidoInputPort).consultar(idStatusPedido);
        verify(pedidoOutputPort, times(0)).salvarPedido(any());
    }


    private static Pedido getPedido(Long idPedido, Long idStatusPedido) {
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);
        return pedido;
    }

    private static Pagamento getPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setUrlPagamento("xxxx-url.com");
        pagamento.setQrCode("xxxx");
        return pagamento;
    }

    private StatusPedido criarStatusPedido(Long id, String nome) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(id);
        statusPedido.setNome(nome);
        return statusPedido;
    }
}
