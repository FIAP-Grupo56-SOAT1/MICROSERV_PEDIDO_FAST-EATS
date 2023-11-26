package br.com.fiap.fasteats.core.usecase.impl.unit;

import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.impl.pedido.CancelarPedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("CancelarPedidoUseCaseUnitTest")
class CancelarPedidoUseCaseUnitTest {

    private CancelarPedidoUseCase cancelarPedidoUseCase;
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cancelarPedidoUseCase = new CancelarPedidoUseCase(
                pedidoInputPort,
                alterarPedidoStatusInputPort
        );
    }

    @Test
    @DisplayName("Deve cancelar um pedido")
    void testeCancelarPedido() {
        Long idPedido = 1L;
        Long idStatusPedidoCancelado = 4L;

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(1L);

        Pedido pedidoCancelado = new Pedido();
        pedidoCancelado.setId(idPedido);
        pedidoCancelado.setStatusPedido(idStatusPedidoCancelado);
        pedidoCancelado.setDataHoraFinalizado(LocalDateTime.now());

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        when(alterarPedidoStatusInputPort.cancelado(idPedido)).thenReturn(pedidoCancelado);
        when(pedidoInputPort.atualizar(pedido)).thenReturn(pedido);

        Pedido resultado = cancelarPedidoUseCase.cancelar(idPedido);

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(idStatusPedidoCancelado, resultado.getStatusPedido());
        assertNotNull(resultado.getDataHoraFinalizado());
        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(alterarPedidoStatusInputPort, times(1)).cancelado(idPedido);
    }

    @Test
    @DisplayName("Deve lançar ProdutoNotFound ao cancelar um pedido inexistente")
    void testeCancelarPedidoInexistente() {
        Long idPedido = 1L;

        when(pedidoInputPort.consultar(idPedido)).thenThrow(new ProdutoNotFound("Pedido não encontrado id " + idPedido));

        assertThrows(ProdutoNotFound.class, () -> cancelarPedidoUseCase.cancelar(idPedido));
        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(alterarPedidoStatusInputPort, never()).cancelado(idPedido);
        verify(pedidoInputPort, never()).atualizar(any(Pedido.class));
    }
}
