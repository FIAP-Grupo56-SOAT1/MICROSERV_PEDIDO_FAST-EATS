package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.dataprovider.constants.StatusMercadoPagoConstants;
import br.com.fiap.fasteats.core.dataprovider.PagamentoExternoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.StatusPagamentoInputPort;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoMercadoPago;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.*;
import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_RECUSADO;

@Component
@RequiredArgsConstructor
public class PagamentoExternoAdapter implements PagamentoExternoOutputPort {
    private final PagamentoInputPort pagamentoInputPort;
    private final StatusPagamentoInputPort statusPagamentoInputPort;
    private final IntegracaoMercadoPago integracaoMercadoPago;

    @Override
    public PagamentoExterno consultar(PagamentoExterno pagamentoExternoRequisicao) {
        return integracaoMercadoPago.consultarPagamento(pagamentoExternoRequisicao);
    }

    @Override
    public PagamentoExterno cancelarPagamento(Long pagamentoExternoId) {
        return integracaoMercadoPago.cancelarPagamento(pagamentoExternoId);
    }

    @Override
    public Pagamento recuperarPagamentoDePagamentoExterno(PagamentoExterno pagamentoExternoRequisicao) {
        PagamentoExterno pagamentoExterno = consultar(pagamentoExternoRequisicao);
        Pagamento pagamento = pagamentoInputPort.consultarPorIdPagamentoExterno(pagamentoExterno.getId());
        pagamento.setStatusPagamento(conveterStatusPagamento(pagamentoExterno));
        return pagamento;
    }

    private StatusPagamento conveterStatusPagamento(PagamentoExterno pagamentoExterno) {
        switch (pagamentoExterno.getStatus()){
            case  StatusMercadoPagoConstants.STATUS_APPROVED,
                    StatusMercadoPagoConstants.STATUS_AUTHORIZED
                    ->{
                return statusPagamentoInputPort.consultarPorNome(STATUS_PAGO);
            }
            case  StatusMercadoPagoConstants.STATUS_IN_PROCESS,
                    StatusMercadoPagoConstants.STATUS_IN_MEDIATION,
                    StatusMercadoPagoConstants.STATUS_PENDING ->{
                return statusPagamentoInputPort.consultarPorNome(STATUS_EM_PROCESSAMENTO);
            }
            case  StatusMercadoPagoConstants.STATUS_CANCELLED,
                    StatusMercadoPagoConstants.STATUS_CHARGED_BACK,
                    StatusMercadoPagoConstants.STATUS_REFUNDED ->{
                return statusPagamentoInputPort.consultarPorNome(STATUS_CANCELADO);
            }
            case  StatusMercadoPagoConstants.STATUS_REJECTED->{
                return statusPagamentoInputPort.consultarPorNome(STATUS_RECUSADO);
            }
            default -> {
                return new StatusPagamento();
            }
        }
    }
}
