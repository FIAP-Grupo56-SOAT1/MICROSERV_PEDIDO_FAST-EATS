package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.PagamentoNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import br.com.fiap.fasteats.dataprovider.client.mapper.PagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoExternoResponse;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.*;
import static java.util.Arrays.asList;
import static java.util.Objects.*;


@Component
@RequiredArgsConstructor
public class IntegracaoPagamentoImpl implements IntegracaoPagamento {

    private final Logger logger = LoggerFactory.getLogger(IntegracaoPagamentoImpl.class);

    private final RestTemplate restTemplate;

    private final PagamentoMapper pagamentoMapper;

    @Value("${URL_SERVICE}")
    private String URL_BASE;

    private final String URI = "/pagamentos";

    @Override
    public List<Pagamento> listar() {
        try {
               ResponseEntity<PagamentoResponse[]> pagamentosResponse =
                    restTemplate.getForEntity(URL_BASE + URI,PagamentoResponse[].class);

            return pagamentoMapper.
                    toPagamentos(stream(requireNonNull(pagamentosResponse.getBody()))
                    .collect(Collectors.toList()));
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro ao consultar pagamentos " + ex.getMessage());
        }
    }

    @Override
    public Optional<Pagamento> consultar(Long id) {
        try {
            PagamentoResponse pagamentosResponse =
                    restTemplate.getForObject(URL_BASE + URI +"/{id}",PagamentoResponse.class,id);

            return Optional.of(pagamentoMapper.toPagamento(pagamentosResponse));
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }

    @Override
    public Optional<Pagamento> consultarPorPedidoId(long pedidoId) {
        try {
            PagamentoResponse pagamentosResponse =
                    restTemplate.getForObject(URL_BASE +
                            URI +"/{pedidoId}/consultar-pagamento-por-id-pedido",PagamentoResponse.class,pedidoId);

            return Optional.of(pagamentoMapper.toPagamento(pagamentosResponse));
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }

    @Override
    public Pagamento salvarPagamento(Pagamento pagamento) {
        try {
            //TODO como vai ser o ENDPOINT SALVAR PAGAMENTOS?
            PagamentoResponse pagamentosResponse =
                    restTemplate.postForObject(URL_BASE +
                            URI,pagamento,PagamentoResponse.class);

            return pagamentoMapper.toPagamento(pagamentosResponse);
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }

    @Override
    public Pagamento atualizarPagamento(Pagamento pagamento) {
        try {
            //TODO como vai ser o ENDPOINT ATUALIZAR PAGAMENTOS?
            PagamentoResponse pagamentosResponse =
                    restTemplate.patchForObject(URL_BASE +
                            URI+"/{id}",pagamento,PagamentoResponse.class,pagamento.getId());

            return pagamentoMapper.toPagamento(pagamentosResponse);
        } catch (Exception ex) {
            logger.error("Erro retorno microservice pagamentos ", ex.getCause());
            throw new PagamentoNotFound("Erro retorno microservice pagamentos " + ex.getMessage());
        }
    }

    @Override
    public Optional<Pagamento> consultarPorIdPagamentoExterno(Long idPagamentoExterno) {
        //TODO precisa implementar endpoint no microservico de pagamento
        return listar()
                .stream()
                .filter(pagamento -> pagamento.getIdPagamentoExterno().equals(idPagamentoExterno))
                .findAny();
    }
}