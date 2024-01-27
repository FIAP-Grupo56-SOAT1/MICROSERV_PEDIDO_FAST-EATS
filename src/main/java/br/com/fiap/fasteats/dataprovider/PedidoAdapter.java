package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.dataprovider.repository.PedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.PedidoEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.PedidoEntityMapper;
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
        PedidoEntity pedidoEntitySalvo = pedidoRepository.saveAndFlush(pedidoEntity);
        pedidoRepository.flush();
        return pedidoEntityMapper.toPedido(pedidoEntitySalvo);
    }

    @Override
    public Optional<Pedido> consultarPedido(Long id) {
        return pedidoRepository.findById(id).map(pedidoEntityMapper::toPedido);
    }

    @Override
    public List<Pedido> listar() {
        return pedidoRepository.findAll().stream().map(pedidoEntityMapper::toPedido).toList();
    }

    @Override
    public List<Pedido> consultarPedidoAndamento(Long id) {
        return pedidoRepository.consultarPedidoAndamento(id).stream().map(pedidoEntityMapper::toPedido).toList();
    }

    @Override
    public List<Pedido> listarPedidosAndamento() {
        return pedidoRepository.listarPedidosAndamento().stream().map(pedidoEntityMapper::toPedido).toList();
    }

    @Override
    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
