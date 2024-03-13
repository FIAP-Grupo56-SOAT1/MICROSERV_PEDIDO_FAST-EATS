package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.CancelarPedidoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CancelarPedidoAdapter implements CancelarPedidoOutputPort {
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    private final PagamentoOutputPort pagamentoOutputPort;

    @Override
    @Transactional
    public Pedido cancelar(Long idPedido) {
        if(existePagamento(idPedido)){
            pagamentoOutputPort.cancelarPagamento(idPedido);
            return alterarPedidoStatusInputPort.aguardandoCancelamento(idPedido);
        }
        return alterarPedidoStatusInputPort.cancelado(idPedido);
    }

    private boolean existePagamento(Long idPedido){
        try{
            return pagamentoOutputPort.consultarPorPedidoId(idPedido).isPresent();
        }catch (Exception e){
            return false;
        }
    }
}
