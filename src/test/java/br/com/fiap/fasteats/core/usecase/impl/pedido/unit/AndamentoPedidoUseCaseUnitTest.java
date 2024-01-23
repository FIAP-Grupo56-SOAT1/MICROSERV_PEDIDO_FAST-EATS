package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AndamentoPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AndamentoPedidoInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AndamentoPedidoUseCaseUnitTest")
class AndamentoPedidoUseCaseUnitTest {

    private AndamentoPedidoInputPort andamentoPedidoUseCase;

    @Mock
    private PedidoOutputPort pedidoOutputPort;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        andamentoPedidoUseCase = new AndamentoPedidoUseCase(pedidoOutputPort);
    }



    @Test
    @DisplayName("Deve consultar lista de pedidos em andamento")
    void testeConsultarAndamentoPedido() {
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

        List<Pedido> resultado = andamentoPedidoUseCase.consultarPedidosEmAndamento();

        assertNotNull(resultado);
        assertEquals(pedidos.size(), resultado.size());
        verify(pedidoOutputPort, times(1)).listarPedidosAndamento();
    }

    @Test
    @DisplayName("Deve consultar um pedido em andamento")
    void testeConsultarUmPedidoAndamentoPedido() {
        Long idPedido = 1L;
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido() {
            {
                setId(idPedido);
                setDataHoraRecebimento(LocalDateTime.now());
            }
        });

        when(pedidoOutputPort.consultarPedidoAndamento(anyLong())).thenReturn(pedidos);

        Pedido resultado = andamentoPedidoUseCase.consultarAndamentoPedido(anyLong());

        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        verify(pedidoOutputPort, times(1)).consultarPedidoAndamento(anyLong());
    }

    @Test
    @DisplayName("Não deve encontrar um pedido em andamento")
    void testeConsultarUmPedidoAndamentoPedidoNotFound() {
        List<Pedido> pedidos = new ArrayList<>();

        when(pedidoOutputPort.consultarPedidoAndamento(anyLong())).thenReturn(pedidos);

        assertThrows(PedidoNotFound.class, () -> andamentoPedidoUseCase.consultarAndamentoPedido(anyLong()));
        verify(pedidoOutputPort, times(1)).consultarPedidoAndamento(anyLong());
    }


}
