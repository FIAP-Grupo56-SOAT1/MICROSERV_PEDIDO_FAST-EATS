package br.com.fiap.fasteats.dataprovider.client;

import br.com.fiap.fasteats.core.domain.model.Pagamento;

import java.util.List;
import java.util.Optional;


public interface IntegracaoPagamento {

    List<Pagamento> listar();

    Optional<Pagamento> consultar(Long id);

    Optional<Pagamento> consultarPorPedidoId(long pedidoId);

    Pagamento gerarPagamento(Long idPedido, Long idFormaPagamento);

    Pagamento consultarPorIdPagamentoExterno(Long idPagamentoExterno);
}
