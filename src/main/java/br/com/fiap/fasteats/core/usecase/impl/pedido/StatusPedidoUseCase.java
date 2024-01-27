package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.StatusPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.StatusPedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;

import java.util.List;

public class StatusPedidoUseCase implements StatusPedidoInputPort {

    private final StatusPedidoOutputPort statusPedidoOutputPort;

    public StatusPedidoUseCase(StatusPedidoOutputPort statusPedidoOutputPort) {
        this.statusPedidoOutputPort = statusPedidoOutputPort;
    }

    @Override
    public StatusPedido criar(StatusPedido statusPedido) {
        statusPedido.setNome(statusPedido.getNome().toUpperCase());
        statusPedido.setAtivo(true);
        return statusPedidoOutputPort.criar(statusPedido);
    }

    @Override
    public StatusPedido consultar(Long id) {
        return statusPedidoOutputPort.consultar(id).orElseThrow(() -> new StatusPedidoNotFound("StatusPedido id:" + id + " não encontrado"));
    }

    @Override
    public StatusPedido atualizar(StatusPedido statusPedido) {
        if (statusPedido.getAtivo() == null) statusPedido.setAtivo(true);
        statusPedido.setNome(statusPedido.getNome().toUpperCase());
        return statusPedidoOutputPort.atualizar(statusPedido);
    }

    @Override
    public void deletar(Long id) {
        statusPedidoOutputPort.deletar(id);
    }

    @Override
    public List<StatusPedido> listar() {
        return statusPedidoOutputPort.listar().orElseThrow(() -> new StatusPedidoNotFound("Não foram encontrados registros no StatusPedido"));
    }

    @Override
    public StatusPedido consultarPorNome(String nome) {
        return statusPedidoOutputPort.consultarPorNome(nome).orElseThrow(() -> new StatusPedidoNotFound("StatusPedido com nome:" + nome + " não encontrado"));
    }
}
