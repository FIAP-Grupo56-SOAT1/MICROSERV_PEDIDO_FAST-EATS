package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteOutputPort {

    Cliente salvarCliente(Cliente cliente);

    Optional<Cliente> consultarCliente(String cpf);

    Optional<List<Cliente>> listar();

    void deletar(String cpf);

    Optional<Cliente> consultarClientePorID(Long id);
}
