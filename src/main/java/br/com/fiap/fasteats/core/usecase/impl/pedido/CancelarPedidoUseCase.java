package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.CancelarPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.CancelarPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;

public class CancelarPedidoUseCase implements CancelarPedidoInputPort {
    private final CancelarPedidoOutputPort cancelarPedidoOutputPort;
    private final AlterarPedidoStatusValidator alterarPedidoStatusValidator;

    public CancelarPedidoUseCase(CancelarPedidoOutputPort cancelarPedidoOutputPort, AlterarPedidoStatusValidator alterarPedidoStatusValidator) {
        this.cancelarPedidoOutputPort = cancelarPedidoOutputPort;
        this.alterarPedidoStatusValidator = alterarPedidoStatusValidator;
    }

    @Override
    public Pedido cancelar(Long idPedido) {
        alterarPedidoStatusValidator.validarCancelado(idPedido);
        return cancelarPedidoOutputPort.cancelar(idPedido);
    }
}
