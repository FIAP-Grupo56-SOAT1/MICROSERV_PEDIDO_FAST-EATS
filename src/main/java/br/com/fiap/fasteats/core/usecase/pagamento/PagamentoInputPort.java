package br.com.fiap.fasteats.core.usecase.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;

import java.util.List;

public interface PagamentoInputPort {
    List<Pagamento> listar();

    Pagamento consultarPorIdPedido(Long idPedido);

    Pagamento criar(Pagamento pagamento);

    Pagamento consultar(Long pagamentoId);

    Pagamento consultarPorIdPagamentoExterno(Long pagamentoExternoId);
}
