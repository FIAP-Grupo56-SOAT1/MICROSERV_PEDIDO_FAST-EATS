package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.PagamentoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import br.com.fiap.fasteats.dataprovider.client.exeption.MicroservicoPagamentoException;
import br.com.fiap.fasteats.dataprovider.client.mapper.PagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class IntegracaoPagamentoImpl implements IntegracaoPagamento {
    private final Logger logger = LoggerFactory.getLogger(IntegracaoPagamentoImpl.class);

    private final RestTemplate restTemplate;

    private final PagamentoMapper pagamentoMapper;

    @Value("${URL_PAGAMENTO_SERVICE}")
    private String URL_BASE;

    private final String URI = "/pagamentos";
    private final String URI_GERAR_PAGAMENTO = "/gerar-pagamento";

    @Override
    public Optional<Pagamento> consultar(Long id) {
        try {
            PagamentoResponse pagamentosResponse =
                    restTemplate.getForObject(URL_BASE + URI + "/{id}", PagamentoResponse.class, id);

            return Optional.of(pagamentoMapper.toPagamento(pagamentosResponse));
        } catch (Exception ex) {
            logger.info("Erro retorno microservice pagamentos ", ex.getCause());
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
    public Pagamento gerarPagamento(Long idPedido, Long idFormaPagamento) {
        try {
            PagamentoResponse pagamentosResponse =
                    restTemplate.postForObject(URL_BASE +
                                    URI_GERAR_PAGAMENTO + "/pedido/{idPedido}/forma-pagamento/{idFormaPagamento}",
                            null, PagamentoResponse.class, idPedido, idFormaPagamento);

            return pagamentoMapper.toPagamento(pagamentosResponse);
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new MicroservicoPagamentoException("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }
}