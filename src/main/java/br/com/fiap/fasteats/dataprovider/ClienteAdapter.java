package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.ClienteOutputPort;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.dataprovider.repository.ClienteRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.ClienteEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutputPort {
    private final ClienteRepository clienteRepository;
    private final ClienteEntityMapper clienteEntityMapper;

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteEntityMapper.toClienteEntity(cliente);
        ClienteEntity clienteEntitySalvo = clienteRepository.save(clienteEntity);
        return clienteEntityMapper.toCliente(clienteEntitySalvo);
    }

    @Override
    public Optional<Cliente> consultarCliente(String cpf) {
        return Optional.ofNullable(clienteEntityMapper.toCliente(clienteRepository.findByCpf(cpf)));
    }

    @Override
    public Optional<List<Cliente>> listar() {
        var clientesEntity = clienteRepository.findByAtivo(true);
        var clientes = clientesEntity.stream()
                .map(clienteEntityMapper::toCliente)
                .toList();
        return Optional.of(clientes);
    }

    @Override
    public void deletar(String cpf) {
        var cliente = clienteRepository.findByCpf(cpf);
        clienteRepository.deleteById(cliente.getId());
    }

    @Override
    public Optional<Cliente> consultarClientePorID(Long id) {
        return clienteRepository.findById(id).map(clienteEntityMapper::toCliente);
    }
}
