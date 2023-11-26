package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AndamentoPedidoInputPort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class AndamentoPedidoUseCase implements AndamentoPedidoInputPort {
    private final PedidoOutputPort pedidoOutputPort;

    public AndamentoPedidoUseCase(PedidoOutputPort pedidoOutputPort) {
        this.pedidoOutputPort = pedidoOutputPort;
    }

    @Override
    public Pedido consultarAndamentoPedido(Long idPedido) {
        List<Pedido> pedidosEmAndamento = pedidoOutputPort.consultarPedidoAndamento(idPedido);
        if (pedidosEmAndamento.isEmpty()) {
            throw new PedidoNotFound("Pedido em andamento não encontrado id " + idPedido);
        }
        adicionarTempoEspera(pedidosEmAndamento);
        return pedidosEmAndamento.get(0);
    }

    @Override
    public List<Pedido> consultarPedidosEmAndamento() {
        List<Pedido> pedidosEmAndamento = pedidoOutputPort.listarPedidosAndamento();
        adicionarTempoEspera(pedidosEmAndamento);
        return pedidosEmAndamento;
    }

    private void adicionarTempoEspera(List<Pedido> pedidosEmAndamento) {
        pedidosEmAndamento.forEach(p -> p.setTempoEspera(calcularTempoEspera(p.getDataHoraRecebimento())));
    }

    private String calcularTempoEspera(LocalDateTime dataHoraRecebimento) {
        Duration duracao = Duration.between(dataHoraRecebimento, LocalDateTime.now());
        long horas = duracao.toHours();
        long minutos = duracao.toMinutesPart();
        long segundos = duracao.toSecondsPart();
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}
