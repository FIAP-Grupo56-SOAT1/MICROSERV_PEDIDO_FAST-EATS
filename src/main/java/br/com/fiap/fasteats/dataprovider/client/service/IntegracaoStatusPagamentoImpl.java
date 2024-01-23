package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.PagamentoNotFound;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoStatusPagamento;
import br.com.fiap.fasteats.dataprovider.client.exeption.MicroservicoPagamentoException;
import br.com.fiap.fasteats.dataprovider.client.mapper.FormaPagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.mapper.StatusPagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.response.FormaPagamentoResponse;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import br.com.fiap.fasteats.dataprovider.client.response.StatusPagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;


@Component
@RequiredArgsConstructor
public class IntegracaoStatusPagamentoImpl implements IntegracaoStatusPagamento {

    private final Logger logger = LoggerFactory.getLogger(IntegracaoStatusPagamentoImpl.class);

    private final RestTemplate restTemplate;

    private final StatusPagamentoMapper statusPagamentoMapper;

    @Value("${URL_PAGAMENTO_SERVICE}")
    private String URL_BASE;

    private final String URI = "/status-pagamentos";

    @Override
    public List<StatusPagamento> listar() {
        try {
            ResponseEntity<StatusPagamentoResponse[]> statusPagamentosResponse =
                    restTemplate.getForEntity(URL_BASE + URI,StatusPagamentoResponse[].class);

            return statusPagamentoMapper.
                    toStatusPagamentos(stream(requireNonNull(statusPagamentosResponse.getBody()))
                            .collect(Collectors.toList()));
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new MicroservicoPagamentoException("Erro ao consultar pagamentos " + ex.getMessage());
        }
    }

    @Override
    public StatusPagamento consultarPorNome(String nome) {
        try {
            StatusPagamentoResponse statusPagamentoResponse =
                    restTemplate.getForObject(URL_BASE + URI +"/status-pagamentos/{nome}", StatusPagamentoResponse.class,nome);

            return statusPagamentoMapper.toStatusPagamento(statusPagamentoResponse);
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new MicroservicoPagamentoException("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }


    @Override
    public StatusPagamento consultar(Long id) {
        try {
            StatusPagamentoResponse statusPagamentoResponse =
                    restTemplate.getForObject(URL_BASE + URI +"/{id}", StatusPagamentoResponse.class,id);

            return statusPagamentoMapper.toStatusPagamento(statusPagamentoResponse);
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new MicroservicoPagamentoException("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }
}