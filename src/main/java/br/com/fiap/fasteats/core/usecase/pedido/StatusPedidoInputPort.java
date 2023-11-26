package br.com.fiap.fasteats.core.usecase.pedido;

import br.com.fiap.fasteats.core.domain.model.StatusPedido;

import java.util.List;

public interface StatusPedidoInputPort {
    StatusPedido criar(StatusPedido statusPedido);

    StatusPedido consultar(Long id);

    StatusPedido atualizar(StatusPedido statusPedido);

    void deletar(Long id);

    List<StatusPedido> listar();

    StatusPedido consultarPorNome(String nome);
}
