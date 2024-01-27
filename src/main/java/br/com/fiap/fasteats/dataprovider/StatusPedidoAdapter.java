package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.StatusPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.dataprovider.repository.StatusPedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.mapper.StatusPedidoEntityMapper;
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
        return salvar(statusPedido);
    }

    @Override
    public Optional<StatusPedido> consultar(Long id) {
        return statusPedidoRepository.findById(id).map(statusPedidoEntityMapper::toStatusPedido);
    }

    @Override
    public StatusPedido atualizar(StatusPedido statusPedido) {
        return salvar(statusPedido);
    }

    @Override
    public void deletar(Long id) {
        statusPedidoRepository.deleteById(id);
    }

    @Override
    public Optional<List<StatusPedido>> listar() {
        return Optional.of(statusPedidoRepository.findAll()
                .stream().map(statusPedidoEntityMapper::toStatusPedido).toList());
    }

    @Override
    public Optional<StatusPedido> consultarPorNome(String nome) {
        return statusPedidoRepository.findByNome(nome.toUpperCase())
                .stream().findFirst().map(statusPedidoEntityMapper::toStatusPedido);
    }

    private StatusPedido salvar(StatusPedido statusPedido) {
        var statusPedidoEntity = statusPedidoEntityMapper.toStatusPedidoEntity(statusPedido);
        return statusPedidoEntityMapper.toStatusPedido(statusPedidoRepository.save(statusPedidoEntity));
    }
}

