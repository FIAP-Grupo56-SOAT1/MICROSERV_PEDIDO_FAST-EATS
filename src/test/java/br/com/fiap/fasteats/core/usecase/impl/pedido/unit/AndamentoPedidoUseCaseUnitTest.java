package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AndamentoPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AndamentoPedidoInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AndamentoPedidoUseCaseUnitTest")
class AndamentoPedidoUseCaseUnitTest {
    @Mock
    private PedidoOutputPort pedidoOutputPort;
    @InjectMocks
    private AndamentoPedidoUseCase andamentoPedidoUseCase;
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
    @DisplayName("Deve consultar lista de pedidos em andamento")
    void testeConsultarAndamentoPedido() {
        //Arrange
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido() {
            {
                setId(1L);
                setDataHoraRecebimento(LocalDateTime.now());
            }
        });
        pedidos.add(new Pedido() {
            {
                setId(2L);
                setDataHoraRecebimento(LocalDateTime.now());
            }
        });

        when(pedidoOutputPort.listarPedidosAndamento()).thenReturn(pedidos);
        //Act
        List<Pedido> resultado = andamentoPedidoUseCase.consultarPedidosEmAndamento();
        //Assert
        assertNotNull(resultado);
        assertEquals(pedidos.size(), resultado.size());
        verify(pedidoOutputPort, times(1)).listarPedidosAndamento();
    }

    @Test
    @DisplayName("Deve consultar um pedido em andamento")
    void testeConsultarUmPedidoAndamentoPedido() {
        //Arrange
        Long idPedido = 1L;
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido() {
            {
                setId(idPedido);
                setDataHoraRecebimento(LocalDateTime.now());
            }
        });

        when(pedidoOutputPort.consultarPedidoAndamento(anyLong())).thenReturn(pedidos);
        //Act
        Pedido resultado = andamentoPedidoUseCase.consultarAndamentoPedido(anyLong());
        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        verify(pedidoOutputPort, times(1)).consultarPedidoAndamento(anyLong());
    }

    @Test
    @DisplayName("Não deve encontrar um pedido em andamento")
    void testeConsultarUmPedidoAndamentoPedidoNotFound() {
        //Arrange
        List<Pedido> pedidos = new ArrayList<>();
        //Act
        when(pedidoOutputPort.consultarPedidoAndamento(anyLong())).thenReturn(pedidos);
        //Assert
        assertThrows(PedidoNotFound.class, () -> andamentoPedidoUseCase.consultarAndamentoPedido(anyLong()));
        verify(pedidoOutputPort, times(1)).consultarPedidoAndamento(anyLong());
    }
}
