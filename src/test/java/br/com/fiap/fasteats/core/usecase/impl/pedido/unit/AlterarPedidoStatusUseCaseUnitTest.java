package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PedidoUseCaseUnitTest")
class AlterarPedidoStatusUseCaseUnitTest {

    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    @Mock
    private PedidoOutputPort pedidoOutputPort;

    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;

    @Mock
    private PagamentoOutputPort pagamentoOutputPort;

    @Mock
    private AlterarPedidoStatusValidator alterarPedidoStatusValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alterarPedidoStatusInputPort = new AlterarPedidoStatusUseCase(alterarPedidoStatusValidator,
                statusPedidoInputPort, pedidoOutputPort, pagamentoOutputPort);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para aguardandoPagamento")
    void testeAguardandoPagamento() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 2L;
        StatusPedido statusPedido = criarStatusPedido(2L, STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);

        Pagamento pagamento = getPagamento();

        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarAguardandoPagamento(anyLong());

        //Act
        Pedido resultado = alterarPedidoStatusInputPort.aguardandoPagamento(anyLong());

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
        Pagamento pagamento = getPagamento();
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarPago(anyLong());
        //Act
        Pedido resultado = alterarPedidoStatusInputPort.pago(anyLong());
        // Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Test
    @DisplayName("Deve atualizar o status de um pedido para recebido")
    void testeRecebido() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedido = 4L;
        StatusPedido statusPedido = criarStatusPedido(idStatusPedido, STATUS_PEDIDO_RECEBIDO);
        Pagamento pagamento = getPagamento();
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarRecebido(anyLong());
        //Act
        Pedido resultado = alterarPedidoStatusInputPort.recebido(anyLong());
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
        Pagamento pagamento = getPagamento();
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarEmPreparo(anyLong());
        //Act
        Pedido resultado = alterarPedidoStatusInputPort.emPreparo(anyLong());
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
        Pagamento pagamento = getPagamento();
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarPronto(anyLong());
        //Act
        Pedido resultado = alterarPedidoStatusInputPort.pronto(anyLong());
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
        Pagamento pagamento = getPagamento();
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarFinalizado(anyLong());
        //Act
        Pedido resultado = alterarPedidoStatusInputPort.finalizado(anyLong());
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
        Pagamento pagamento = getPagamento();
        Pedido pedido = getPedido(idPedido, idStatusPedido);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarCancelado(anyLong());
        //Act
        Pedido resultado = alterarPedidoStatusInputPort.cancelado(anyLong());
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
        doNothing().when(alterarPedidoStatusValidator).validarAguardandoPagamento(anyLong());

        //Act
        Pedido resultado = null;
        for(String nomeStatusPedido : statusPedidoPossiveis) {
            StatusPedido statusPedido = criarStatusPedido(idStatusPedido, nomeStatusPedido);

            when(statusPedidoInputPort.consultar(idStatusPedido)).thenReturn(statusPedido);
            when(statusPedidoInputPort.consultarPorNome(nomeStatusPedido)).thenReturn(statusPedido);


            resultado = alterarPedidoStatusInputPort.atualizarStatusPedido(idPedido, idStatusPedido);
        }

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedido, resultado.getStatusPedido());
        verify(statusPedidoInputPort, times(7)).consultarPorNome(anyString());
        verify(pedidoOutputPort, times(7)).salvarPedido(pedido);
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
        assertThrows(RuntimeException.class, () -> alterarPedidoStatusInputPort.atualizarStatusPedido(idPedido, idStatusPedido));
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
