package br.com.fiap.fasteats.core.usecase.impl.pagamento.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoExternoOutputPort;
import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.CancelarPagamentoExternoUseCase;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.validator.CancelarPagamentoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.MERCADO_PAGO;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Teste Unitário - Cancelar Pagamento Externo")
class CancelarPagamentoExternoUseCaseUnitTest {
    @Mock
    private PagamentoInputPort pagamentoInputPort;
    @Mock
    private PagamentoExternoOutputPort pagamentoExternoOutputPort;
    @Mock
    private CancelarPagamentoValidator cancelarPagamentoValidator;
    @InjectMocks
    private CancelarPagamentoExternoUseCase cancelarPagamentoExternoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cancelar um pagamento externo")
    void cancelar() {
        //Arrange
        Long pagamentoExternoId = 1L;
        Long idPedido = 1L;
        Pagamento pagamento = getPagamentoExterno(pagamentoExternoId, idPedido);

        when(pagamentoInputPort.consultarPorIdPagamentoExterno(pagamentoExternoId)).thenReturn(pagamento);

        //Act
        cancelarPagamentoExternoUseCase.cancelar(pagamentoExternoId);

        //Assert
        verify(cancelarPagamentoValidator).validarCancelarPagamento(idPedido);
        verify(pagamentoExternoOutputPort).cancelarPagamento(pagamentoExternoId);
    }

    private Pagamento getPagamentoExterno(Long pagamentoId, Long pedidoId) {

        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoId);
        pagamento.setFormaPagamento(getFormaPagamentoExterno(2L));
        pagamento.setIdPagamentoExterno(null);
        pagamento.setQrCode(null);
        pagamento.setUrlPagamento(null);
        pagamento.setPedido(new Pedido());
        pagamento.getPedido().setId(pedidoId);
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
}