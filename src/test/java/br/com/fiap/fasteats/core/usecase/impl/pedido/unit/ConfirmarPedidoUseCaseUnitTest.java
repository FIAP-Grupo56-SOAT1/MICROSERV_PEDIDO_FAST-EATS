package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.*;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ConfirmarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ConfirmarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
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
    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    @Mock
    private PagamentoOutputPort pagamentoOutputPort;
    @Mock
    private PedidoValidator pedidoValidator;
    @InjectMocks
    private ConfirmarPedidoInputPort confirmarPedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve confirmar um pedido")
    void testeConfirmarPedido() {
        //Arrange
        Long idPedido = 1L;
        Long idStatusPedidoAguardandoPagamento = 2L;
        Long tipoPagamentoId = 2L;

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(idPedido);
        produtoPedido.setQuantidade(1);
        produtoPedido.setValor(10.0);

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(1L);
        pedido.getProdutos().add(produtoPedido);

        Pedido pedidoAguardandoPagamento = new Pedido();
        pedidoAguardandoPagamento.setId(idPedido);
        pedidoAguardandoPagamento.setStatusPedido(idStatusPedidoAguardandoPagamento);
        pedidoAguardandoPagamento.getProdutos().add(produtoPedido);
        pedidoAguardandoPagamento.setQrCode("qrCode");
        pedidoAguardandoPagamento.setUrlPagamento("urlPagamento");

        FormaPagamento formaPagamento = new FormaPagamento(1L, MERCADO_PAGO, true);
        StatusPagamento statusPagamento = new StatusPagamento(1L, STATUS_EM_PROCESSAMENTO, true);

        LocalDateTime dataHoraTeste = LocalDateTime.now();

        Pagamento pagamento = new Pagamento(1L, formaPagamento, statusPagamento, pedido,
                dataHoraTeste, dataHoraTeste, dataHoraTeste,
                123456L, "qrCode", "urlPagamento");

        Pagamento pagamentoAtualizado = new Pagamento();
        pagamentoAtualizado.setId(1L);
        pagamentoAtualizado.setFormaPagamento(formaPagamento);
        pagamentoAtualizado.setStatusPagamento(statusPagamento);
        pagamentoAtualizado.setPedido(pedido);
        pagamentoAtualizado.setDataHoraCriado(dataHoraTeste);
        pagamentoAtualizado.setDataHoraProcessamento(dataHoraTeste);
        pagamentoAtualizado.setDataHoraFinalizado(dataHoraTeste);
        pagamentoAtualizado.setIdPagamentoExterno(123456L);
        pagamentoAtualizado.setQrCode("qrCode");
        pagamentoAtualizado.setUrlPagamento("urlPagamento");

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        doNothing().when(pedidoValidator).validarAlterarPedido(pedido);
        //when(pagamentoOutputPort.gerarPagamento(pedido.getId(), tipoPagamentoId)).thenReturn(pagamento);
        when(alterarPedidoStatusInputPort.aguardandoPagamento(idPedido)).thenReturn(pedidoAguardandoPagamento);
        //Act
        Pedido resultado = confirmarPedidoUseCase.confirmar(idPedido, tipoPagamentoId);
        //Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(pagamentoAtualizado.getQrCode(), resultado.getQrCode());
        assertEquals(pagamentoAtualizado.getUrlPagamento(), resultado.getUrlPagamento());
        assertEquals(idStatusPedidoAguardandoPagamento, resultado.getStatusPedido());
        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(alterarPedidoStatusInputPort, times(1)).aguardandoPagamento(idPedido);
    }

    @Test
    @DisplayName("Deve lançar ProdutoNotFound ao confirmar um pedido sem produtos")
    void testeConfirmarPedidoInexistente() {
        //Arrange
        Long idPedido = 1L;
        Long tipoPagamentoId = 2L;

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(1L);
        pedido.setProdutos(new ArrayList<>());

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        //Act e Assert
        assertThrows(ProdutoNotFound.class, () -> confirmarPedidoUseCase.confirmar(idPedido, tipoPagamentoId));
        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(pedidoInputPort, never()).atualizar(any(Pedido.class));
    }
}
