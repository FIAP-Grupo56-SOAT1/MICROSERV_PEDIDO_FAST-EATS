package br.com.fiap.fasteats.dataprovider.client;

import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.core.domain.model.Pagamento;

import java.util.List;
import java.util.Optional;


public interface IntegracaoFormaPagamento {

    Optional<FormaPagamento> consultarPorNome(String nome);

    Optional<FormaPagamento> consultar(Long id);
}
