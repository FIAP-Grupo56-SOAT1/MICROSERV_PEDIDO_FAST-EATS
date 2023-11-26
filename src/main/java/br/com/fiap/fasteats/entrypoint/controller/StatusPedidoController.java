package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.entrypoint.controller.mapper.StatusPedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.StatusPedidoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.StatusPedidoResponse;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("status-pedidos")
@RequiredArgsConstructor
@Tag(name = "Status do Pedido", description = "Controller que gerencia os status de pedido")
public class StatusPedidoController {
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final StatusPedidoMapper statusPedidoMapper;

    @PostMapping
    @Operation(summary = "Criar status pedido", description = "Cria status de um pedido.")
    public ResponseEntity<StatusPedidoResponse> criarStatusPedido(@Valid @RequestBody StatusPedidoRequest statusPedidoRequest) {
        StatusPedido statusPedido = statusPedidoMapper.toStatusPedido(statusPedidoRequest);
        StatusPedido statusPedidoCriado = statusPedidoInputPort.criar(statusPedido);
        StatusPedidoResponse statusPedidoResponse = statusPedidoMapper.toStatusPedidoResponse(statusPedidoCriado);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Atualizar status pedido", description = "Atualiza status de um pedido.")
    public ResponseEntity<StatusPedidoResponse> atualizarStatusPedido(@PathVariable("id") final Long id, @RequestBody StatusPedidoRequest statusPedidoRequest) {
        StatusPedido statusPedido = statusPedidoMapper.toStatusPedido(statusPedidoRequest);
        statusPedido.setId(id);
        StatusPedido statusPedidoAtualizado = statusPedidoInputPort.atualizar(statusPedido);
        StatusPedidoResponse statusPedidoResponse = statusPedidoMapper.toStatusPedidoResponse(statusPedidoAtualizado);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar status pedido", description = "Deleta status de um pedido.")
    public ResponseEntity<Void> deletarStatusPedido(@PathVariable("id") final Long id) {
        statusPedidoInputPort.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar status pedido por ID", description = "Consulta status de um pedido por ID.")
    public ResponseEntity<StatusPedidoResponse> consultarStatusPedido(@PathVariable("id") final Long id) {
        StatusPedido statusPedido = statusPedidoInputPort.consultar(id);
        StatusPedidoResponse statusPedidoResponse = statusPedidoMapper.toStatusPedidoResponse(statusPedido);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @GetMapping("consultar-por-nome/{nome}")
    @Operation(summary = "Consultar status pedido por nome", description = "Consulta status de um pedido por nome.")
    public ResponseEntity<StatusPedidoResponse> consultarStatusPedidoPorNome(@PathVariable("nome") final String nome) {
        StatusPedido statusPedido = statusPedidoInputPort.consultarPorNome(nome);
        StatusPedidoResponse statusPedidoResponse = statusPedidoMapper.toStatusPedidoResponse(statusPedido);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @GetMapping
    @Operation(summary = "Listar status pedido", description = "Retorna status de pedido")
    public ResponseEntity<List<StatusPedidoResponse>> listarStatusPedidos() {
        List<StatusPedido> statusPedido = statusPedidoInputPort.listar();
        List<StatusPedidoResponse> statusPedidoResponse = statusPedidoMapper.toStatusPedidoResponseList(statusPedido);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }
}
