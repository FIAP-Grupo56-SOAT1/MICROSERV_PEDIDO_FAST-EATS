package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.ClienteMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.ClienteRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.ClienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente", description = "Controller que gerencia os clientes")
public class ClienteController {
    private final ClienteInputPort clienteInputPort;
    private final ClienteMapper clienteMapper;

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cadastra um novo cliente.")
    public ResponseEntity<ClienteResponse> criarCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        var cliente = clienteMapper.toCliente(clienteRequest);
        cliente = clienteInputPort.criar(cliente);
        return new ResponseEntity<>(clienteMapper.toClienteResponse(cliente), HttpStatus.CREATED);
    }

    @GetMapping("{cpf}/pesquisar-por-cpf")
    @Operation(summary = "Consultar cliente por CPF", description = "Retorna os dados do cliente cadastrado por CPF.")
    public ResponseEntity<ClienteResponse> consultarCliente(@PathVariable("cpf") String cpf) {
        Cliente cliente = clienteInputPort.consultar(cpf);
        return new ResponseEntity<>(clienteMapper.toClienteResponse(cliente), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar cliente por ID", description = "Retorna os dados do cliente cadastrado por ID.")
    public ResponseEntity<ClienteResponse> consultarClientePorID(@PathVariable("id") Long id) {
        Cliente cliente = clienteInputPort.consultarPorID(id);
        return new ResponseEntity<>(clienteMapper.toClienteResponse(cliente), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados.")
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        List<Cliente> clientes = clienteInputPort.listar();
        return new ResponseEntity<>(clienteMapper.toClientesResponse(clientes), HttpStatus.OK);
    }

    @PatchMapping("{cpf}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados do cliente cadastrado por CPF.")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable("cpf") String cpf, @RequestBody ClienteRequest clienteRequest) {
        Cliente cliente = clienteMapper.toCliente(clienteRequest);
        cliente.setCpf(cpf);
        Cliente clientAtualizado = clienteInputPort.atualizar(cliente);
        return new ResponseEntity<>(clienteMapper.toClienteResponse(clientAtualizado), HttpStatus.OK);
    }

    @DeleteMapping("{cpf}")
    @Operation(summary = "Deletar cliente", description = "Deleta os dados de um cliente por CPF.")
    public ResponseEntity<Void> deletar(@PathVariable("cpf") String cpf) {
        clienteInputPort.deletar(cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}