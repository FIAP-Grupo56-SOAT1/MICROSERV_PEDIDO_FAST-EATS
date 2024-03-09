package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.PagamentoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import br.com.fiap.fasteats.dataprovider.client.exeption.AwsSQSException;
import br.com.fiap.fasteats.dataprovider.client.exeption.MicroservicoPagamentoException;
import br.com.fiap.fasteats.dataprovider.client.mapper.PagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.request.GerarPagamentoRequest;
import br.com.fiap.fasteats.dataprovider.client.request.NotificarPedidoPagoRequest;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import com.google.gson.Gson;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class IntegracaoPagamentoImpl implements IntegracaoPagamento {
    @Value("${sqs.queue.pagamento.gerar.pagamento}")
    private String filaPagamentoGerarPagamento;
    @Value("${sqs.queue.pagamento.receber-pedido-pago}")
    private String filaPagamentoReceberPedidoPago;
    @Value("${URL_PAGAMENTO_SERVICE}")
    private String URL_BASE;
    private final RestTemplate restTemplate;
    private final SqsTemplate sqsTemplate;
    private final PagamentoMapper pagamentoMapper;
    private final String URI = "/pagamentos";

    @Override
    public Optional<Pagamento> consultar(Long id) {
        try {
            PagamentoResponse pagamentosResponse =
                    restTemplate.getForObject(URL_BASE + URI + "/{id}", PagamentoResponse.class, id);

            return Optional.of(pagamentoMapper.toPagamento(pagamentosResponse));
        } catch (Exception ex) {
            log.info("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }

    @Override
    public Optional<Pagamento> consultarPorPedidoId(long pedidoId) {
        try {
            PagamentoResponse pagamentosResponse =
                    restTemplate.getForObject(URL_BASE +
                            URI + "/{pedidoId}/consultar-pagamento-por-id-pedido", PagamentoResponse.class, pedidoId);

            return Optional.of(pagamentoMapper.toPagamento(pagamentosResponse));
        } catch (Exception ex) {
            throw new MicroservicoPagamentoException("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }

    @Override
    public void gerarPagamento(Long idPedido, Long idFormaPagamento) {
        try {
            GerarPagamentoRequest request = new GerarPagamentoRequest(idPedido, idFormaPagamento);
            String mensagem = new Gson().toJson(request);
            sqsTemplate.send(filaPagamentoGerarPagamento, mensagem);
            log.info(String.format("Pedido %d com a forma de pagamento %d enviado para fila pagamento-gerar-pagamento com sucesso!", idPedido, idFormaPagamento));
        } catch (Exception ex) {
            String resposta = String.format("Erro na comunicação com a fila %s: %s", filaPagamentoGerarPagamento, ex.getMessage());
            log.error(resposta);
            throw new AwsSQSException(resposta);
        }
    }

    @Override
    public void notificarPedidoPago(Long pedidoId) {
        try {
            NotificarPedidoPagoRequest request = new NotificarPedidoPagoRequest(pedidoId);
            String mensagem = new Gson().toJson(request);
            sqsTemplate.send(filaPagamentoReceberPedidoPago, mensagem);
            log.info(String.format("Pedido %d enviado para fila %s com sucesso!", pedidoId, filaPagamentoReceberPedidoPago));
        } catch (Exception ex) {
            String resposta = String.format("Erro na comunicação com a fila %s: %s", filaPagamentoReceberPedidoPago, ex.getMessage());
            log.error(resposta);
            throw new AwsSQSException(resposta);
        }
    }
}