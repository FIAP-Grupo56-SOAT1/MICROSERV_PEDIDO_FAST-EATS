package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ConfirmarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pagamento.GerarPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ConfirmarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ConfirmarPedidoUseCaseUnitTest")
class ConfirmarPedidoUseCaseUnitTest {
    private ConfirmarPedidoInputPort confirmarPedidoUseCase;
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    @Mock
    private GerarPagamentoInputPort gerarPagamentoInputPort;
    @Mock
    private PedidoValidator pedidoValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        confirmarPedidoUseCase = new ConfirmarPedidoUseCase(
                pedidoInputPort,
                alterarPedidoStatusInputPort,
                gerarPagamentoInputPort,
                pedidoValidator);
    }

    @Test
    @DisplayName("Deve confirmar um pedido")
    void testeConfirmarPedido() {
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

        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);

        Pagamento pagamentoAtualizado = new Pagamento();
        pagamentoAtualizado.setId(1L);
        pagamentoAtualizado.setQrCode("qrCode");
        pagamentoAtualizado.setUrlPagamento("urlPagamento");

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);
        doNothing().when(pedidoValidator).validarAlterarPedido(pedido);
        when(gerarPagamentoInputPort.gerar(pedido, tipoPagamentoId)).thenReturn(pagamento);
        when(alterarPedidoStatusInputPort.aguardandoPagamento(idPedido)).thenReturn(pedidoAguardandoPagamento);

        Pedido resultado = confirmarPedidoUseCase.confirmar(idPedido, tipoPagamentoId);

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
        Long idPedido = 1L;
        Long tipoPagamentoId = 2L;

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(1L);
        pedido.setProdutos(new ArrayList<>());

        when(pedidoInputPort.consultar(idPedido)).thenReturn(pedido);

        assertThrows(ProdutoNotFound.class, () -> confirmarPedidoUseCase.confirmar(idPedido, tipoPagamentoId));
        verify(pedidoInputPort, times(1)).consultar(idPedido);
        verify(pedidoInputPort, never()).atualizar(any(Pedido.class));
    }
}
