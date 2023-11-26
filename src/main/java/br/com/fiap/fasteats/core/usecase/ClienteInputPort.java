package br.com.fiap.fasteats.core.usecase;

import br.com.fiap.fasteats.core.domain.model.Cliente;

import java.util.List;

public interface ClienteInputPort {

    Cliente criar(Cliente cliente);

    Cliente consultar(String cpf);

    List<Cliente> listar();

    Cliente atualizar(Cliente cliente);

    void deletar(String cpf);

    Boolean clienteExiste(String cpf);

    void validarCliente(Cliente cliente);
}
