package br.com.fiap.fasteats.core.usecase.impl.pagamento.unit;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.CancelarPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.pagamento.AlterarPagamentoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.CancelarPedidoInputPort;
import br.com.fiap.fasteats.core.validator.CancelarPagamentoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.MERCADO_PAGO;
import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.PIX;
import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_CANCELADO;
import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_EM_PROCESSAMENTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Teste Unitário - Cancelar Pagamento")
class CancelarPagamentoUseCaseUnitTest {
    @Mock
    private PagamentoInputPort pagamentoInputPort;
    @Mock
    private AlterarPagamentoStatusInputPort alterarPagamentoStatusInputPort;
    @Mock
    private CancelarPedidoInputPort cancelarPedidoInputPort;
    @Mock
    private CancelarPagamentoValidator cancelarPagamentoValidator;
    @InjectMocks
    private CancelarPagamentoUseCase cancelarPagamentoUseCase;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cancelar um pagamento interno")
    void cancelar() {
        // Arrange
        Long idPedido = 1L;
        Pagamento pagamento = getPagamentoInterno(1L, idPedido);
        Pagamento pagamentoCancelado = getPagamentoInterno(1L, idPedido);
        pagamentoCancelado.setStatusPagamento(getStatusPagamento(2L, STATUS_CANCELADO));

        when(pagamentoInputPort.consultarPorIdPedido(idPedido)).thenReturn(pagamento);
        when(alterarPagamentoStatusInputPort.cancelado(pagamento.getId())).thenReturn(pagamentoCancelado);

        // Act
        Pagamento resultado = cancelarPagamentoUseCase.cancelar(idPedido);

        // Assert
        assertEquals(STATUS_CANCELADO, resultado.getStatusPagamento().getNome());
        verify(cancelarPagamentoValidator).validarCancelarPagamento(idPedido);
        verify(cancelarPedidoInputPort).cancelar(idPedido);
        verify(alterarPagamentoStatusInputPort).cancelado(pagamento.getId());
    }

    @Test
    @DisplayName("Deve gerar erro ao tentar cancelar um pagamento externo")
    void testErroCancelarPagamentoExterno() {
        // Arrange
        Long idPedido = 1L;
        Pagamento pagamentoExterno = getPagamentoExterno(1L, 1L);

        when(pagamentoInputPort.consultarPorIdPedido(idPedido)).thenReturn(pagamentoExterno);

        // Act & Assert
        assertThrows(RegraNegocioException.class, () -> cancelarPagamentoUseCase.cancelar(idPedido));
    }


    private Pagamento getPagamentoInterno(Long pagamentoId, Long pedidoId) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoId);
        pagamento.setFormaPagamento(getFormaPagamentoInterno(1L));
        pagamento.setStatusPagamento(getStatusPagamento(1L, STATUS_EM_PROCESSAMENTO));
        pagamento.setIdPagamentoExterno(null);
        pagamento.setQrCode(null);
        pagamento.setUrlPagamento(null);
        pagamento.setPedido(new Pedido());
        pagamento.getPedido().setId(pedidoId);
        return pagamento;
    }

    private FormaPagamento getFormaPagamentoInterno(Long formaPagamentoId) {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(formaPagamentoId);
        formaPagamento.setNome(PIX);
        formaPagamento.setExterno(false);
        formaPagamento.setAtivo(true);
        return formaPagamento;
    }

    private Pagamento getPagamentoExterno(Long pagamentoId, Long pedidoId) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoId);
        pagamento.setPedido(new Pedido());
        pagamento.getPedido().setId(pedidoId);
        pagamento.setFormaPagamento(getFormaPagamentoExterno(2L));
        pagamento.setIdPagamentoExterno(null);
        pagamento.setQrCode(null);
        pagamento.setUrlPagamento(null);
        return pagamento;
    }

    private FormaPagamento getFormaPagamentoExterno(Long formaPagamentoId) {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(formaPagamentoId);
        formaPagamento.setNome(MERCADO_PAGO);
        formaPagamento.setExterno(true);
        formaPagamento.setAtivo(true);
        return formaPagamento;
    }

    private StatusPagamento getStatusPagamento(Long statusPagamentoId, String nomeStatusPagamento) {
        StatusPagamento statusPagamento = new StatusPagamento();
        statusPagamento.setId(statusPagamentoId);
        statusPagamento.setNome(nomeStatusPagamento);
        return statusPagamento;
    }
}