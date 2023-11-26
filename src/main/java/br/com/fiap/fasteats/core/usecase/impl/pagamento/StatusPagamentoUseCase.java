package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.domain.exception.StatusPedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.StatusPagamentoInputPort;
import br.com.fiap.fasteats.core.dataprovider.StatusPagamentoOutputPort;

import java.util.List;

public class StatusPagamentoUseCase implements StatusPagamentoInputPort {

    private final StatusPagamentoOutputPort statusPagamentoOutputPort;

    public StatusPagamentoUseCase(StatusPagamentoOutputPort statusPagamentoOutputPort) {
        this.statusPagamentoOutputPort = statusPagamentoOutputPort;
    }

    @Override
    public StatusPagamento consultar(Long id) {
        return statusPagamentoOutputPort.consultar(id).orElseThrow(() -> new StatusPedidoNotFound("StatusPedido id:" + id + " não encontrado"));
    }

    @Override
    public List<StatusPagamento> listar() {
        return statusPagamentoOutputPort.listar();
    }

    @Override
    public StatusPagamento consultarPorNome(String nome) {
        return statusPagamentoOutputPort.consultarPorNome(nome).orElseThrow(() -> new StatusPedidoNotFound("StatusPedido com nome:" + nome + " não encontrado"));
    }
}
