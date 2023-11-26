package br.com.fiap.fasteats.core.usecase.pedido;

import br.com.fiap.fasteats.core.domain.model.Pedido;

import java.util.List;

public interface PedidoInputPort {
    Pedido criar(Pedido pedido);

    Pedido consultar(Long id);

    List<Pedido> listar();

    Pedido atualizar(Pedido pedido);

    void deletar(Long id);

    void atualizarValorPedido(Pedido pedido);
}