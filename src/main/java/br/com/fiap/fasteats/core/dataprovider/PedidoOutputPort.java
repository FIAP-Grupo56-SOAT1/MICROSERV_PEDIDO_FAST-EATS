package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoOutputPort {

    Pedido salvarPedido(Pedido pedido);

    Optional<Pedido> consultarPedido(Long id);

    List<Pedido> listar();

    List<Pedido> listarPedidosAndamento();

    List<Pedido> consultarPedidoAndamento(Long id);

    void deletar(Long id);
}
