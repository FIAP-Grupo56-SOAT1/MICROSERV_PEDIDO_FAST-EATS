package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.dataprovider.repository.PedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.PedidoEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.PedidoEntityMapper;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PedidoAdapter implements PedidoOutputPort {
    private final PedidoRepository pedidoRepository;
    private final PedidoEntityMapper pedidoEntityMapper;

    @Override
    public Pedido salvarPedido(Pedido pedido) {
        PedidoEntity pedidoEntity = pedidoEntityMapper.toPedidoEntity(pedido);
        pedidoEntity = pedidoRepository.saveAndFlush(pedidoEntity);
        pedidoRepository.flush();
        return pedidoEntityMapper.toPedido(pedidoEntity);
    }

    @Override
    public Optional<Pedido> consultarPedido(Long id) {
        Optional<PedidoEntity> pedidoEntity = pedidoRepository.findById(id);

        if (pedidoEntity.isEmpty()) {
            return Optional.empty();
        }

        Pedido pedido = pedidoEntityMapper.toPedido(pedidoEntity.get());
        return Optional.of(pedido);
    }

    @Override
    public List<Pedido> listar() {
        List<PedidoEntity> pedidosEntity = pedidoRepository.findAll();
        return pedidoEntityMapper.toPedidos(pedidosEntity);
    }

    @Override
    public List<Pedido> consultarPedidoAndamento(Long id) {
        List<PedidoEntity> pedidosEntity = pedidoRepository.consultarPedidoAndamento(id);
        return pedidoEntityMapper.toPedidos(pedidosEntity);
    }

    @Override
    public List<Pedido> listarPedidosAndamento() {
        List<PedidoEntity> pedidosEntity = pedidoRepository.listarPedidosAndamento();
        return pedidoEntityMapper.toPedidos(pedidosEntity);
    }

    @Override
    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
