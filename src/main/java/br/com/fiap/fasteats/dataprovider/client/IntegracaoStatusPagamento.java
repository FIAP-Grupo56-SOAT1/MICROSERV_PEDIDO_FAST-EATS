package br.com.fiap.fasteats.dataprovider.client;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;

import java.util.List;
import java.util.Optional;


public interface IntegracaoStatusPagamento {

    List<StatusPagamento> listar();

    Optional<StatusPagamento> consultarPorNome(String nome);

    Optional<StatusPagamento> consultar(Long id);
}
