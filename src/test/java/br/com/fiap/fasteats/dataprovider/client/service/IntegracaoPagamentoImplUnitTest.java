package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.PagamentoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.exeption.MicroservicoPagamentoException;
import br.com.fiap.fasteats.dataprovider.client.mapper.PagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class IntegracaoPagamentoImplUnitTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private PagamentoMapper pagamentoMapper;
    @Value("${URL_PAGAMENTO_SERVICE}")
    private String URL_BASE;
    @InjectMocks
    private IntegracaoPagamentoImpl integracaoPagamento;
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
    void consultar_DeveRetornarPagamento_QuandoIdExistente() {
        // Arrange
        Long id = 1L;
        PagamentoResponse pagamentoResponse = new PagamentoResponse();
        when(restTemplate.getForObject(URL_BASE + "/pagamentos/{id}", PagamentoResponse.class, id)).thenReturn(pagamentoResponse);
        when(pagamentoMapper.toPagamento(pagamentoResponse)).thenReturn(new Pagamento());

        // Act
        Optional<Pagamento> resultado = integracaoPagamento.consultar(id);

        // Assert
        assertTrue(resultado.isPresent());
    }

    @Test
    void consultar_DeveLancarPagamentoNotFound_QuandoIdNaoExistente() {
        // Arrange
        Long id = 1L;
        when(restTemplate.getForObject(URL_BASE + "/pagamentos/{id}", PagamentoResponse.class, id)).thenThrow(new RuntimeException());

        // Act/Assert
        assertThrows(PagamentoNotFound.class, () -> integracaoPagamento.consultar(id));
    }

    @Test
    void consultarPorPedidoId_DeveRetornarPagamento_QuandoPedidoIdExistente() {
        // Arrange
        long pedidoId = 1L;
        PagamentoResponse pagamentoResponse = new PagamentoResponse();
        when(restTemplate.getForObject(URL_BASE + "/pagamentos/{pedidoId}/consultar-pagamento-por-id-pedido", PagamentoResponse.class, pedidoId)).thenReturn(pagamentoResponse);
        when(pagamentoMapper.toPagamento(pagamentoResponse)).thenReturn(new Pagamento());

        // Act
        Optional<Pagamento> resultado = integracaoPagamento.consultarPorPedidoId(pedidoId);

        // Assert
        assertTrue(resultado.isPresent());
    }

    @Test
    void consultarPorPedidoId_DeveLancarMicroservicoPagamentoException_QuandoPedidoIdNaoExistente() {
        // Arrange
        long pedidoId = 1L;
        when(restTemplate.getForObject(URL_BASE + "/pagamentos/{pedidoId}/consultar-pagamento-por-id-pedido", PagamentoResponse.class, pedidoId)).thenThrow(new RuntimeException());

        // Act/Assert
        assertThrows(MicroservicoPagamentoException.class, () -> integracaoPagamento.consultarPorPedidoId(pedidoId));
    }

    @Test
    void gerarPagamento_DeveRetornarPagamento_QuandoGeracaoBemSucedida() {
        // Arrange
        Long idPedido = 1L;
        Long idFormaPagamento = 2L;
        PagamentoResponse pagamentoResponse = new PagamentoResponse();
        when(restTemplate.postForObject(URL_BASE + "/gerar-pagamento/pedido/{idPedido}/forma-pagamento/{idFormaPagamento}", null, PagamentoResponse.class, idPedido, idFormaPagamento)).thenReturn(pagamentoResponse);
        when(pagamentoMapper.toPagamento(pagamentoResponse)).thenReturn(new Pagamento());

        // Act
        Pagamento resultado = integracaoPagamento.gerarPagamento(idPedido, idFormaPagamento);

        // Assert
        assertNotNull(resultado);
    }

    @Test
    void gerarPagamento_DeveLancarMicroservicoPagamentoException_QuandoGeracaoFalha() {
        // Arrange
        Long idPedido = 1L;
        Long idFormaPagamento = 2L;
        when(restTemplate.postForObject(URL_BASE + "/gerar-pagamento/pedido/{idPedido}/forma-pagamento/{idFormaPagamento}", null, PagamentoResponse.class, idPedido, idFormaPagamento)).thenThrow(new RuntimeException());

        // Act/Assert
        assertThrows(MicroservicoPagamentoException.class, () -> integracaoPagamento.gerarPagamento(idPedido, idFormaPagamento));
    }
}