package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoStatusPagamento;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class IntegracaoStatusPagamentoImpl implements IntegracaoStatusPagamento {

    private final Logger logger = LoggerFactory.getLogger(IntegracaoStatusPagamentoImpl.class);


    @Override
    public List<StatusPagamento> listar() {
        return null;
    }

    @Override
    public Optional<StatusPagamento> consultarPorNome(String nome) {
        return Optional.empty();
    }

    @Override
    public Optional<StatusPagamento> consultar(Long id) {
        return Optional.empty();
    }
}