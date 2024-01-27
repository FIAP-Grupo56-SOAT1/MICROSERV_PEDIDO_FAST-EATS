package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.usecase.ProdutoInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.ProdutoMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.ProdutoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.ProdutoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
@Tag(name = "Produto", description = "Controller que gerencia os produtos")
public class ProdutoController {
    private final ProdutoInputPort produtoInputPort;
    private final ProdutoMapper produtoMapper;

    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto.")
    public ResponseEntity<ProdutoResponse> criarProduto(@Valid @RequestBody ProdutoRequest produtoRequest) {
        Produto produto = produtoMapper.toProduto(produtoRequest);
        Produto produtoCriado = produtoInputPort.criar(produto);
        ProdutoResponse produtoResponse = produtoMapper.toProdutoResponse(produtoCriado);
        return ResponseEntity.ok().body(produtoResponse);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto.")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable("id") final Long id, @RequestBody ProdutoRequest produtoRequest) {
        Produto produto = produtoMapper.toProduto(produtoRequest);
        produto.setId(id);
        Produto produtoAtualizado = produtoInputPort.atualizar(produto);
        ProdutoResponse statusPedidoResponse = produtoMapper.toProdutoResponse(produtoAtualizado);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar produto", description = "Deleta um produto.")
    public ResponseEntity<Void> deletarProduto(@PathVariable("id") final Long id) {
        produtoInputPort.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar produto por ID", description = "Retorna um produto.")
    public ResponseEntity<ProdutoResponse> consultarproduto(@PathVariable("id") final Long id) {
        Produto produto = produtoInputPort.consultar(id);
        ProdutoResponse statusPedidoResponse = produtoMapper.toProdutoResponse(produto);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @GetMapping
    @Operation(summary = "Listar produto", description = "Retorna todos os produtos cadastrados.")
    public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
        List<Produto> produtos = produtoInputPort.listar();
        List<ProdutoResponse> produtosResponse = produtoMapper.toProdutoResponseList(produtos);
        return ResponseEntity.ok().body(produtosResponse);
    }

    @GetMapping("consultar-por-nome/{nome}")
    @Operation(summary = "Consultar produto por nome", description = "Retorna um produto por nome.")
    public ResponseEntity<ProdutoResponse> consultarProdutoPorNome(@PathVariable("nome") final String nome) {
        Produto produto = produtoInputPort.consultarPorNome(nome);
        ProdutoResponse produtoResponse = produtoMapper.toProdutoResponse(produto);
        return ResponseEntity.ok().body(produtoResponse);
    }

    @GetMapping("consultar-por-categoria/{categoriaId}")
    @Operation(summary = "Consultar produto por categoria", description = "Retorna um produto por ID da categoria.")
    public ResponseEntity<List<ProdutoResponse>> consultarProdutoPorCategoria(@PathVariable("categoriaId") final Long categoriaId) {
        List<Produto> produto = produtoInputPort.consultarPorCategoria(categoriaId);
        List<ProdutoResponse> produtoResponse = produtoMapper.toProdutoResponseList(produto);
        return ResponseEntity.ok().body(produtoResponse);
    }
}
