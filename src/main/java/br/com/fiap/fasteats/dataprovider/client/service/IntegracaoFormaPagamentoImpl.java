package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.PagamentoNotFound;
import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoFormaPagamento;
import br.com.fiap.fasteats.dataprovider.client.mapper.FormaPagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.response.FormaPagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class IntegracaoFormaPagamentoImpl implements IntegracaoFormaPagamento {

    private final Logger logger = LoggerFactory.getLogger(IntegracaoFormaPagamentoImpl.class);

    private final RestTemplate restTemplate;

    private final FormaPagamentoMapper formaPagamentoMapper;

    @Value("${URL_SERVICE}")
    private String URL_BASE;

    private final String URI = "/forma-pagamento";

    @Override
    public Optional<FormaPagamento> consultarPorNome(String nome) {
        try {
            FormaPagamentoResponse formaPagamentoResponse =
                    restTemplate.getForObject(URL_BASE + URI +"/{nome}", FormaPagamentoResponse.class,nome);

            return Optional.of(formaPagamentoMapper.toFormaPagamento(formaPagamentoResponse));
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }

    @Override
    public Optional<FormaPagamento> consultar(Long id) {
        try {
            FormaPagamentoResponse formaPagamentoResponse =
                    restTemplate.getForObject(URL_BASE + URI +"/{id}", FormaPagamentoResponse.class,id);

            return Optional.of(formaPagamentoMapper.toFormaPagamento(formaPagamentoResponse));
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }
}