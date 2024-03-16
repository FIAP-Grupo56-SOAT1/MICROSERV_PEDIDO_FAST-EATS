package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.GerarPagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.*;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ConfirmarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ConfirmarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.MERCADO_PAGO;
import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_EM_PROCESSAMENTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ConfirmarPedidoUseCaseUnitTest")
class ConfirmarPedidoUseCaseUnitTest {
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private GerarPagamentoOutputPort gerarPagamentoOutputPort;
    @Mock
    private PedidoValidator pedidoValidator;
    @InjectMocks
    private ConfirmarPedidoUseCase confirmarPedidoUseCase;
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
    @DisplayName("Deve confirmar um pedido com sucesso")
    void testeConfirmarPedidoComSucesso() {
        //Arrange
        Long idPedido = 1L;
        Long tipoPagamentoId = 2L;

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.getProdutos().add(new ProdutoPedido());

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        doNothing().when(pedidoValidator).validarAlterarPedido(pedido);
        when(gerarPagamentoOutputPort.gerar(pedido.getId(), tipoPagamentoId)).thenReturn(pedido);

        //Act
        Pedido resultado = confirmarPedidoUseCase.confirmar(idPedido, tipoPagamentoId);

        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(pedidoValidator, times(1)).validarAlterarPedido(pedido);
        verify(gerarPagamentoOutputPort, times(1)).gerar(pedido.getId(), tipoPagamentoId);
    }

    @Test
    @DisplayName("Deve lançar ProdutoNotFound ao confirmar um pedido sem produtos")
    void testeConfirmarPedidoSemProdutos() {
        //Arrange
        Long idPedido = 1L;
        Long tipoPagamentoId = 2L;

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);

        //Act e Assert
        assertThrows(ProdutoNotFound.class, () -> confirmarPedidoUseCase.confirmar(idPedido, tipoPagamentoId));
        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(pedidoValidator, never()).validarAlterarPedido(pedido);
        verify(gerarPagamentoOutputPort, never()).gerar(pedido.getId(), tipoPagamentoId);
    }
}
