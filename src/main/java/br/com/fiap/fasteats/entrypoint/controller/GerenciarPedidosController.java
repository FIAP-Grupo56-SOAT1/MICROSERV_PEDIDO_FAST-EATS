package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.entrypoint.controller.mapper.GerenciarPedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.response.GerenciarPedidoResponse;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AndamentoPedidoInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("gerenciar/pedidos")
@RequiredArgsConstructor
@Tag(name = "Gerenciar Pedidos", description = "Controller que gerencia detalhes de um pedido.")
public class GerenciarPedidosController {
    private final AndamentoPedidoInputPort andamentoPedidoInputPort;
    private final GerenciarPedidoMapper gerenciarPedidoMapper;

    @GetMapping("{id}")
    @Operation(summary = "Consultar detalhes pedido", description = "Retorna os detalhes do pedido.")
    public ResponseEntity<GerenciarPedidoResponse> consultarDetalhePedido(@PathVariable("id") Long id) {
        Pedido pedido = andamentoPedidoInputPort.consultarAndamentoPedido(id);
        return new ResponseEntity<>(gerenciarPedidoMapper.toGerenciarPedidoResponse(pedido), HttpStatus.OK);
    }

    @GetMapping("/andamento")
    @Operation(summary = "Consultar detalhes pedido em andamento", description = "Retorna os detalhes do pedido em andamento.")
    public ResponseEntity<List<GerenciarPedidoResponse>> consultarPedidosEmAndamento() {
        List<Pedido> pedidos = andamentoPedidoInputPort.consultarPedidosEmAndamento();
        return new ResponseEntity<>(gerenciarPedidoMapper.toGerenciarPedidosResponse(pedidos), HttpStatus.OK);
    }
}
