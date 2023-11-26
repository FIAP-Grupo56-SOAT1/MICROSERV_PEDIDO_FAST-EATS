package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.FormaPagamento;

import java.util.List;
import java.util.Optional;

public interface FormaPagamentoOutputPort {

    Optional<FormaPagamento> consultar(Long id);

    Optional<FormaPagamento> consultarPorNome(String nome);
}
