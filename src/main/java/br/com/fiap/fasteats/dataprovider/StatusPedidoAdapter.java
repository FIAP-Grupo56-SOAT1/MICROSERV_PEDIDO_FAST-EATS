package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.dataprovider.repository.StatusPedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.mapper.StatusPedidoEntityMapper;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.dataprovider.StatusPedidoOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatusPedidoAdapter implements StatusPedidoOutputPort {
    private final StatusPedidoRepository statusPedidoRepository;
    private final StatusPedidoEntityMapper statusPedidoEntityMapper;

    @Override
    public StatusPedido criar(StatusPedido statusPedido) {
        var statusPedidoEntity = statusPedidoEntityMapper.toStatusPedidoEntity(statusPedido);
        var statusPedidoEntitySalvo = statusPedidoRepository.save(statusPedidoEntity);
        return statusPedidoEntityMapper.toStatusPedido(statusPedidoEntitySalvo);
    }

    @Override
    public Optional<StatusPedido> consultar(Long id) {
        return statusPedidoRepository.findById(id).map(statusPedidoEntityMapper::toStatusPedido);
    }

    @Override
    public StatusPedido atualizar(StatusPedido statusPedido) {
        var statusPedidoEntity = statusPedidoEntityMapper.toStatusPedidoEntity(statusPedido);
        var statusPedidoEntityAtualizado = statusPedidoRepository.save(statusPedidoEntity);
        return statusPedidoEntityMapper.toStatusPedido(statusPedidoEntityAtualizado);
    }

    @Override
    public void deletar(Long id) {
        statusPedidoRepository.deleteById(id);
    }

    @Override
    public Optional<List<StatusPedido>> listar() {
        var statusPedidoEntities = statusPedidoRepository.findAll();
        var statusPedidos = statusPedidoEntities.stream()
                .map(statusPedidoEntityMapper::toStatusPedido)
                .toList();
        return Optional.of(statusPedidos);
    }

    @Override
    public Optional<StatusPedido> consultarPorNome(String nome) {
        var statusPedidoEntity = statusPedidoRepository.findByNome(nome.toUpperCase());
        var statusPedidos = statusPedidoEntity.stream()
                .map(statusPedidoEntityMapper::toStatusPedido)
                .toList();
        return statusPedidos.stream().findFirst();
    }
}

