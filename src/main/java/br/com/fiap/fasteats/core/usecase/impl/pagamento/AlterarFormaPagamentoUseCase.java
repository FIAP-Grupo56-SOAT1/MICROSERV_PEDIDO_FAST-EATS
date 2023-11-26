package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.FormaPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.AlterarFormaPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.GerarPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;

public class AlterarFormaPagamentoUseCase implements AlterarFormaPagamentoInputPort {
    private final PagamentoInputPort pagamentoInputPort;
    private final FormaPagamentoInputPort formaPagamentoInputPort;
    private final GerarPagamentoInputPort gerarPagamentoInputPort;

    public AlterarFormaPagamentoUseCase(PagamentoInputPort pagamentoInputPort, FormaPagamentoInputPort formaPagamentoInputPort, GerarPagamentoInputPort gerarPagamentoInputPort) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.formaPagamentoInputPort = formaPagamentoInputPort;
        this.gerarPagamentoInputPort = gerarPagamentoInputPort;
    }

    @Override
    public Pagamento alterarFormaPagamento(Long pagamentoId, Long formaPagamentoId) {
        Pagamento pagamento = pagamentoInputPort.consultar(pagamentoId);
        Pagamento pagamentoAtualizado = gerarPagamentoInputPort.gerar(pagamento.getPedido(), formaPagamentoId);
        pagamentoAtualizado.setFormaPagamento(formaPagamentoInputPort.consultar(formaPagamentoId));
        return pagamentoInputPort.atualizar(pagamentoAtualizado);
    }
}
