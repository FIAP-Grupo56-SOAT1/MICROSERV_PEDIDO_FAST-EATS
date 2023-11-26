package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.usecase.pedido.ProdutoPedidoInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.PedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.ProdutoPedidoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.PedidoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produto-pedido")
@RequiredArgsConstructor
@Tag(name = "Produto do Pedido", description = "Controller que gerencia os itens do pedido")
public class ProdutoPedidoController {    
    private final ProdutoPedidoInputPort produtoPedidoInputPort;
    private final PedidoMapper pedidoMapper;

    @PostMapping("{idPedido}/produtos")
    @Operation(summary = "Adicionar produto no pedido", description = "Adiciona produtos dentro do pedido.")
    public ResponseEntity<PedidoResponse> adicionarProduto(@PathVariable("idPedido") Long idPedido,
                                                           @RequestBody ProdutoPedidoRequest produtoPedidoRequest) {
        ProdutoPedido produtoPedido = pedidoMapper.toProdutoPedido(produtoPedidoRequest);
        produtoPedido.setIdPedido(idPedido);
        Pedido pedidoAtualizado = produtoPedidoInputPort.adicionarProdutoPedido(produtoPedido);
        PedidoResponse pedidoResponse = pedidoMapper.toPedidoResponse(pedidoAtualizado);
        return new ResponseEntity<>(pedidoResponse, HttpStatus.OK);
    }

    @PatchMapping("{idPedido}/produtos/{idProduto}")
    @Operation(summary = "Atualizar produtos do pedido", description = "Atualiza os produtos do pedido.")
    public ResponseEntity<PedidoResponse> atualizarProduto(@PathVariable("idPedido") Long idPedido,
                                                           @PathVariable("idProduto") Long idProduto,
                                                           @RequestBody ProdutoPedidoRequest produtoPedidoRequest) {
        ProdutoPedido produtoPedido = pedidoMapper.toProdutoPedido(produtoPedidoRequest);
        produtoPedido.setIdPedido(idPedido);
        produtoPedido.setIdProduto(idProduto);
        Pedido pedidoAtualizado = produtoPedidoInputPort.atualizarProdutoPedido(produtoPedido);
        PedidoResponse pedidoResponse = pedidoMapper.toPedidoResponse(pedidoAtualizado);
        return new ResponseEntity<>(pedidoResponse, HttpStatus.OK);
    }

    @DeleteMapping("{idPedido}/produtos/{idProduto}")
    @Operation(summary = "Deletar produtos do pedido", description = "Deleta os produtos do pedido.")
    public ResponseEntity<PedidoResponse> removerProduto(@PathVariable("idPedido") Long idPedido,
                                                         @PathVariable("idProduto") Long idProduto) {
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(idPedido);
        produtoPedido.setIdProduto(idProduto);
        Pedido pedidoAtualizado = produtoPedidoInputPort.removerProdutoPedido(produtoPedido);
        PedidoResponse pedidoResponse = pedidoMapper.toPedidoResponse(pedidoAtualizado);
        return new ResponseEntity<>(pedidoResponse, HttpStatus.OK);
    }
}