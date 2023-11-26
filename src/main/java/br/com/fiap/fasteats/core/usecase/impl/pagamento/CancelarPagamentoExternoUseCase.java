package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.dataprovider.PagamentoExternoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.CancelarPagamentoExternoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.validator.CancelarPagamentoValidator;

public class CancelarPagamentoExternoUseCase implements CancelarPagamentoExternoInputPort {
    private final PagamentoInputPort pagamentoInputPort;
    private final PagamentoExternoOutputPort pagamentoExternoOutputPort;
    private final CancelarPagamentoValidator cancelarPagamentoValidator;

    public CancelarPagamentoExternoUseCase(PagamentoInputPort pagamentoInputPort,
                                           PagamentoExternoOutputPort pagamentoExternoOutputPort,
                                           CancelarPagamentoValidator cancelarPagamentoValidator) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.pagamentoExternoOutputPort = pagamentoExternoOutputPort;
        this.cancelarPagamentoValidator = cancelarPagamentoValidator;
    }

    @Override
    public void cancelar(Long pagamentoExternoId) {
        Pagamento pagamento = pagamentoInputPort.consultarPorIdPagamentoExterno(pagamentoExternoId);
        cancelarPagamentoValidator.validarCancelarPagamento(pagamento.getPedido().getId());
        pagamentoExternoOutputPort.cancelarPagamento(pagamentoExternoId);
    }
}
