package br.com.fiap.fasteats.dataprovider.client;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.domain.model.Pedido;

import java.util.List;
import java.util.Optional;


public interface IntegracaoPagamento {

    List<Pagamento> listar();

    Optional<Pagamento> consultar(Long id);

    Optional<Pagamento> consultarPorPedidoId(long pedidoId);

    Pagamento salvarPagamento(Pagamento pagamento);

    Pagamento atualizarPagamento(Pagamento pagamento);

    Optional<Pagamento> consultarPorIdPagamentoExterno(Long idPagamentoExterno);
}
