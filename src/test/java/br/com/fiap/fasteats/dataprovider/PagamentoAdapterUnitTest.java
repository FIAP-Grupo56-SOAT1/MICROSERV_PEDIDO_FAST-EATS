package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.MERCADO_PAGO;
import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_EM_PROCESSAMENTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PagamentoAdapterUnitTest {
    @Mock
    private IntegracaoPagamento integracaoPagamento;
    @InjectMocks
    private PagamentoAdapter pagamentoAdapterUnderTest;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void consultarPorPedidoId() {
        // Arrange
        long pedidoId = 1L;
        Pagamento pagamento = getPagamento(1L);
        FormaPagamento formaPagamento = getFormaPagamento();
        StatusPagamento statusPagamento = getStatusPagamento();
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setStatusPagamento(statusPagamento);

        when(integracaoPagamento.consultarPorPedidoId(pedidoId)).thenReturn(Optional.of(pagamento));

        // Act
        Optional<Pagamento> result = pagamentoAdapterUnderTest.consultarPorPedidoId(pedidoId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pagamento.getId(), result.get().getId());
        assertEquals(pagamento.getFormaPagamento().getId(), result.get().getFormaPagamento().getId());
        assertEquals(pagamento.getFormaPagamento().getNome(), result.get().getFormaPagamento().getNome());
        assertEquals(pagamento.getFormaPagamento().getAtivo(), result.get().getFormaPagamento().getAtivo());
        assertEquals(pagamento.getFormaPagamento().getExterno(), result.get().getFormaPagamento().getExterno());
        assertEquals(pagamento.getStatusPagamento().getId(), result.get().getStatusPagamento().getId());
        assertEquals(pagamento.getStatusPagamento().getNome(), result.get().getStatusPagamento().getNome());
        assertEquals(pagamento.getStatusPagamento().getAtivo(), result.get().getStatusPagamento().getAtivo());
        verify(integracaoPagamento).consultarPorPedidoId(pedidoId);
    }

    @Test
    void gerarPagamento() {
        // Arrange
        Long idPedido = 1L;
        Long idFormaPagamento = 1L;
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);

        //when(integracaoPagamento.gerarPagamento(idPedido, idFormaPagamento)).thenReturn(pagamento);

        // Act
        //Pagamento result = pagamentoAdapterUnderTest.gerarPagamento(idPedido, idFormaPagamento);

        // Assert
        //assertEquals(pagamento.getId(), result.getId());
        verify(integracaoPagamento).gerarPagamento(idPedido, idFormaPagamento);
    }

    private Pagamento getPagamento(Long pagamentoId) {
        return new Pagamento(pagamentoId,
                getFormaPagamento(),
                getStatusPagamento(),
                new Pedido(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                123456789L,
                "QrCode",
                "UrlPagamento");
    }

    private FormaPagamento getFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(1L);
        formaPagamento.setNome(MERCADO_PAGO);
        formaPagamento.setAtivo(true);
        formaPagamento.setExterno(true);
        return formaPagamento;
    }

    private StatusPagamento getStatusPagamento() {
        StatusPagamento statusPagamento = new StatusPagamento();
        statusPagamento.setId(1L);
        statusPagamento.setNome(STATUS_EM_PROCESSAMENTO);
        statusPagamento.setAtivo(true);
        return statusPagamento;
    }
}