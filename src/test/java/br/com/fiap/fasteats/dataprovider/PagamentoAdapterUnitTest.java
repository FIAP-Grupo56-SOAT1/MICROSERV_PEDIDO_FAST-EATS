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

        when(integracaoPagamento.consultarPorPedidoId(pedidoId)).thenReturn(Optional.of(pagamento));

        // Act
        Optional<Pagamento> result = pagamentoAdapterUnderTest.consultarPorPedidoId(pedidoId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pagamento.getId(), result.get().getId());
        verify(integracaoPagamento).consultarPorPedidoId(pedidoId);
    }

    @Test
    void gerarPagamento() {
        // Arrange
        Long idPedido = 1L;
        Long idFormaPagamento = 1L;
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);

        when(integracaoPagamento.gerarPagamento(idPedido, idFormaPagamento)).thenReturn(pagamento);

        // Act
        Pagamento result = pagamentoAdapterUnderTest.gerarPagamento(idPedido, idFormaPagamento);

        // Assert
        assertEquals(pagamento.getId(), result.getId());
        verify(integracaoPagamento).gerarPagamento(idPedido, idFormaPagamento);
    }

    private Pagamento getPagamento(Long pagamentoId) {
        return new Pagamento(pagamentoId,
                new FormaPagamento(),
                new StatusPagamento(),
                new Pedido(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                123456789L,
                "QrCode",
                "UrlPagamento");
    }
}