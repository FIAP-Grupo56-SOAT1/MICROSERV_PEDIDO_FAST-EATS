package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PagamentoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.StatusPagamentoInputPort;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.fiap.fasteats.core.constants.StatusPagamentoConstants.STATUS_EM_PROCESSAMENTO;

public class PagamentoUseCase implements PagamentoInputPort {
    private final PagamentoOutputPort pagamentoOutputPort;
    private final StatusPagamentoInputPort statusPagamentoInputPort;

    public PagamentoUseCase(PagamentoOutputPort pagamentoOutputPort, StatusPagamentoInputPort statusPagamentoInputPort) {
        this.pagamentoOutputPort = pagamentoOutputPort;
        this.statusPagamentoInputPort = statusPagamentoInputPort;
    }

    @Override
    public List<Pagamento> listar() {
        return pagamentoOutputPort.listar();
    }

    @Override
    public Pagamento consultarPorIdPedido(Long idPedido) {
        return pagamentoOutputPort.consultarPorPedidoId(idPedido).orElseThrow(() -> new PagamentoNotFound("Pagamento não encontrato"));
    }

    @Override
    public Pagamento criar(Pagamento pagamento) {
        pagamento.setDataHoraCriado(LocalDateTime.now());
        pagamento.setStatusPagamento(statusPagamentoInputPort.consultarPorNome(STATUS_EM_PROCESSAMENTO));
        pagamento.setDataHoraProcessamento(LocalDateTime.now());
        return pagamentoOutputPort.salvarPagamento(pagamento);
    }
    @Override
    public Pagamento consultar(Long pagamentoId) {
        return pagamentoOutputPort.consultar(pagamentoId).orElseThrow(() -> new PagamentoNotFound("Pagamento não encontrato"));
    }

    @Override
    public Pagamento consultarPorIdPagamentoExterno(Long pagamentoExternoId) {
        return pagamentoOutputPort.consultarPorIdPagamentoExterno(pagamentoExternoId).orElseThrow(() -> new PagamentoNotFound("Pagamento não encontrado"));
    }
}
