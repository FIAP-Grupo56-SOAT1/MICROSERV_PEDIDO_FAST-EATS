package br.com.fiap.fasteats.core.usecase.impl;

import br.com.fiap.fasteats.core.dataprovider.ClienteOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ClienteNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.exception.ValidarClienteException;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.domain.valueobject.Cpf;
import br.com.fiap.fasteats.core.domain.valueobject.Email;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClienteUseCase implements ClienteInputPort {
    private final ClienteOutputPort clienteOutputPort;

    public ClienteUseCase(ClienteOutputPort clienteOutputPort) {
        this.clienteOutputPort = clienteOutputPort;
    }

    @Override
    public Cliente criar(Cliente cliente) {
        String cpfFormatado = formatarEValidarCpf(cliente.getCpf());
        if (Boolean.TRUE.equals(clienteExiste(cpfFormatado)))
            throw new RegraNegocioException("O Cpf " + cliente.getCpf() + " já está cadastrado");
        cliente.setCpf(cpfFormatado);
        cliente.setAtivo(true);
        validarCliente(cliente);
        return clienteOutputPort.salvarCliente(cliente);
    }

    @Override
    public Cliente consultar(String cpf) {
        String cpfFormatado = formatarEValidarCpf(cpf);
        return clienteOutputPort.consultarCliente(cpfFormatado).orElseThrow(() -> new ClienteNotFound("Cliente não encontrado cpf " + cpfFormatado));
    }

    @Override
    public List<Cliente> listar() {
        return clienteOutputPort.listar().orElseThrow(() -> new ClienteNotFound("Não foram encontrados registros de Clientes"));
    }

    @Override
    public Cliente atualizar(Cliente cliente) {
        if (cliente.getAtivo() == null) cliente.setAtivo(true);
        cliente.setCpf(formatarEValidarCpf(cliente.getCpf()));
        validarCliente(cliente);
        return clienteOutputPort.salvarCliente(cliente);
    }

    @Override
    public Boolean clienteExiste(String cpf) {
        String cpfFormatado = formatarEValidarCpf(cpf);
        return clienteOutputPort.consultarCliente(cpfFormatado).isPresent();
    }

    @Override
    public void deletar(String cpf) {
        String cpfFormatado = formatarEValidarCpf(cpf);
        consultar(cpfFormatado);
        clienteOutputPort.deletar(cpfFormatado);
    }

    @Override
    public void validarCliente(Cliente cliente) {
        validarEmail(cliente);
        if (cliente.getCpf() == null || cliente.getCpf().isEmpty())
            throw new ValidarClienteException("CPF não pode ser vazio");
        formatarEValidarCpf(cliente.getCpf());
    }

    @Override
    public Cliente desativarCliente(Cliente cliente) {
       var clienteDb =  buscarClientePorCpf(cliente.getCpf());
        clienteDb.setAtivo(false);
        return clienteOutputPort.desativarCliente(clienteDb);
    }

    @Override
    public Cliente excluirClienteLgpd(Cliente cliente) {
        var novoCpf = FormatarCpfLgpd(cliente);
        cliente.setCpf(novoCpf);
        return clienteOutputPort.excluirClienteLgpd(cliente);
    }

    private String FormatarCpfLgpd(Cliente cliente) {

        String cpf = cliente.getCpf();
        String cpfInicial = cpf.substring(0,3);
        StringBuilder builder = new StringBuilder();
        builder.append(cpfInicial);

        for (int i = 0; i <= 7; i++) {
            builder.append("X");
        }

        return  builder.toString().trim();
    }

    private String formatarEValidarCpf(String cpf) {
        Cpf cpfFormatado = new Cpf(cpf);
        return cpfFormatado.valor();
    }

    private void validarEmail(Cliente cliente) {
        if(cliente.getEmail() == null || cliente.getEmail().isEmpty()) throw new ValidarClienteException("Email não pode ser vazio");
        Email emailFormatado = new Email(cliente.getEmail());
        cliente.setEmail(emailFormatado.valor());
    }

    private Cliente buscarClientePorCpf(String cpf){
        String cpfFormatado = formatarEValidarCpf(cpf);
        return clienteOutputPort.consultarCliente(cpf).orElseThrow(() -> new ClienteNotFound("Cliente não encontrado cpf " + cpfFormatado));
    }
}
