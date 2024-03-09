package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.ConcluirPedidoPagoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ConcluirPedidoPagoAdapter implements ConcluirPedidoPagoOutputPort {
    private final PedidoOutputPort pedidoOutputPort;
    private final PagamentoOutputPort pagamentoOutputPort;

    @Override
    @Transactional
    public Pedido concluirPagamento(Pedido pedido) {
        Pedido pedidoAtualizado = pedidoOutputPort.salvarPedido(pedido);
        pagamentoOutputPort.notificarPedidoPago(pedido.getId());
        return pedidoAtualizado;
    }
}
