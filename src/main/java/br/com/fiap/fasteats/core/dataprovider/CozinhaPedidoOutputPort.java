package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface CozinhaPedidoOutputPort {
    void receberPedido(Long idPedido);

}
