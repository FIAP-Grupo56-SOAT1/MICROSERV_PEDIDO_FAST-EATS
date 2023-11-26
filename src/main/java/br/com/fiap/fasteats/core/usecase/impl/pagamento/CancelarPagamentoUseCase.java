package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.AlterarPagamentoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.CancelarPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.CancelarPedidoInputPort;
import br.com.fiap.fasteats.core.validator.CancelarPagamentoValidator;

public class CancelarPagamentoUseCase implements CancelarPagamentoInputPort {
    private final PagamentoInputPort pagamentoInputPort;
    private final AlterarPagamentoStatusInputPort alterarPagamentoStatusInputPort;
    private final CancelarPedidoInputPort cancelarPedidoInputPort;
    private final CancelarPagamentoValidator cancelarPagamentoValidator;

    public CancelarPagamentoUseCase(PagamentoInputPort pagamentoInputPort,
                                    AlterarPagamentoStatusInputPort alterarPagamentoStatusInputPort,
                                    CancelarPedidoInputPort cancelarPedidoInputPort,
                                    CancelarPagamentoValidator cancelarPagamentoValidator) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.alterarPagamentoStatusInputPort = alterarPagamentoStatusInputPort;
        this.cancelarPedidoInputPort = cancelarPedidoInputPort;
        this.cancelarPagamentoValidator = cancelarPagamentoValidator;
    }

    @Override
    public Pagamento cancelar(Long pedidoId) {
        Pagamento pagamento = pagamentoInputPort.consultarPorIdPedido(pedidoId);

        if (Boolean.TRUE.equals(pagamento.getFormaPagamento().getExterno()))
            throw new RegraNegocioException("Para cancelar um pagamento externo, utilize o endpoint específico de cada método de pagamento");

        cancelarPagamentoValidator.validarCancelarPagamento(pedidoId);
        cancelarPedidoInputPort.cancelar(pedidoId);
        return alterarPagamentoStatusInputPort.cancelado(pedidoId);
    }
}
