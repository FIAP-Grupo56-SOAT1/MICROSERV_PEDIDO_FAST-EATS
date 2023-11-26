package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoFormaPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class IntegracaoFormaPagamentoImpl implements IntegracaoFormaPagamento {


    @Override
    public Optional<FormaPagamento> consultarPorNome(String nome) {
        return Optional.empty();
    }

    @Override
    public Optional<FormaPagamento> consultar(Long id) {
        return Optional.empty();
    }
}