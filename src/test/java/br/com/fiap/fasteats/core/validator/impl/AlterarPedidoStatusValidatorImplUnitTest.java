package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlterarPedidoStatusValidatorImplUnitTest {
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;
    @InjectMocks
    private AlterarPedidoStatusValidatorImpl alterarPedidoStatusValidatorImpl;
    AutoCloseable openMocks;
    private final Long pedidoId = 1L;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void validarAguardandoPagamento() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido(STATUS_PEDIDO_CRIADO);

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act
        alterarPedidoStatusValidatorImpl.validarAguardandoPagamento(pedidoId);

        // Assert
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarAguardandoPagamentoRegraNegocioException() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido("TESTE");

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> alterarPedidoStatusValidatorImpl.validarAguardandoPagamento(pedidoId));
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarPago() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido(STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act
        alterarPedidoStatusValidatorImpl.validarPago(pedidoId);

        // Assert
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarPagoRegraNegocioException() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido("TESTE");

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> alterarPedidoStatusValidatorImpl.validarPago(pedidoId));
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarRecebido() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido(STATUS_PEDIDO_PAGO);

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act
        alterarPedidoStatusValidatorImpl.validarRecebido(pedidoId);

        // Assert
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarRecebidoRegraNegocioException() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido("TESTE");

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> alterarPedidoStatusValidatorImpl.validarRecebido(pedidoId));
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarEmPreparo() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido(STATUS_PEDIDO_RECEBIDO);

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act
        alterarPedidoStatusValidatorImpl.validarEmPreparo(pedidoId);

        // Assert
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarEmPreparoRegraNegocioException() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido("TESTE");

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> alterarPedidoStatusValidatorImpl.validarEmPreparo(pedidoId));
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarPronto() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido(STATUS_PEDIDO_EM_PREPARO);

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act
        alterarPedidoStatusValidatorImpl.validarPronto(pedidoId);

        // Assert
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarProntoRegraNegocioException() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido("TESTE");

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> alterarPedidoStatusValidatorImpl.validarPronto(pedidoId));
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarFinalizado() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido(STATUS_PEDIDO_PRONTO);

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act
        alterarPedidoStatusValidatorImpl.validarFinalizado(pedidoId);

        // Assert
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarFinalizadoRegraNegocioException() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido("TESTE");

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> alterarPedidoStatusValidatorImpl.validarFinalizado(pedidoId));
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarCancelado() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido("TESTE");

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act
        alterarPedidoStatusValidatorImpl.validarCancelado(pedidoId);

        // Assert
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    @Test
    void validarCanceladoRegraNegocioException() {
        // Arrange
        Pedido pedido = getPedido();
        StatusPedido statusPedido = getStatusPedido(STATUS_PEDIDO_CANCELADO);

        when(pedidoInputPort.consultar(pedidoId)).thenReturn(pedido);
        when(statusPedidoInputPort.consultar(pedidoId)).thenReturn(statusPedido);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> alterarPedidoStatusValidatorImpl.validarCancelado(pedidoId));
        verify(statusPedidoInputPort).consultar(pedidoId);
        verify(pedidoInputPort).consultar(pedidoId);
    }

    private Pedido getPedido() {
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(1L);
        return pedido;
    }

    private StatusPedido getStatusPedido(String nomeStatusPedido) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setNome(nomeStatusPedido);
        return statusPedido;
    }
}