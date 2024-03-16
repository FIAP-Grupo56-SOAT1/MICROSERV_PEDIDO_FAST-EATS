package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.CancelarPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.CancelarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("CancelarPedidoUseCaseUnitTest")
class CancelarPedidoUseCaseUnitTest {
    @Mock
    private CancelarPedidoOutputPort cancelarPedidoOutputPort;
    @Mock
    private AlterarPedidoStatusValidator alterarPedidoStatusValidator;
    @InjectMocks
    private CancelarPedidoUseCase cancelarPedidoUseCase;
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
    @DisplayName("Deve cancelar um pedido com sucesso")
    void testeCancelarPedidoComSucesso() {
        //Arrange
        Long idPedido = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(1L);

        when(cancelarPedidoOutputPort.cancelar(idPedido)).thenReturn(pedido);
        doNothing().when(alterarPedidoStatusValidator).validarCancelado(idPedido);

        //Act
        Pedido resultado = cancelarPedidoUseCase.cancelar(idPedido);

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        verify(cancelarPedidoOutputPort, times(1)).cancelar(idPedido);
        verify(alterarPedidoStatusValidator, times(1)).validarCancelado(idPedido);
    }

    @Test
    @DisplayName("Deve lançar exceção ao cancelar um pedido inválido")
    void testeCancelarPedidoInvalido() {
        //Arrange
        Long idPedido = 1L;

        doThrow(new RuntimeException("Pedido inválido")).when(alterarPedidoStatusValidator).validarCancelado(idPedido);

        //Act e Assert
        assertThrows(RuntimeException.class, () -> cancelarPedidoUseCase.cancelar(idPedido));
        verify(cancelarPedidoOutputPort, never()).cancelar(idPedido);
        verify(alterarPedidoStatusValidator, times(1)).validarCancelado(idPedido);
    }
}
