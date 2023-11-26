package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.entrypoint.controller.mapper.CategoriaMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.CategoriaRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.CategoriaResponse;
import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.usecase.CategoriaInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorias")
@RequiredArgsConstructor
@Tag(name = "Categoria", description = "Controller que gerencia as categorias de produtos.")
public class CategoriaController {
    private final CategoriaInputPort categoriaInputPort;
    private final CategoriaMapper categoriaMapper;

    @PostMapping
    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria de produto.")
    public ResponseEntity<CategoriaResponse> criarCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest) {
        Categoria categoria = categoriaMapper.toCategoria(categoriaRequest);
        Categoria categoriaCriada = categoriaInputPort.criar(categoria);
        CategoriaResponse statusPedidoResponse = categoriaMapper.toCategoriaResponse(categoriaCriada);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria de produto.")
    public ResponseEntity<CategoriaResponse> atualizarCategoria(@PathVariable("id") final Long id, @RequestBody CategoriaRequest statusPedidoRequest) {
        Categoria categoria = categoriaMapper.toCategoria(statusPedidoRequest);
        categoria.setId(id);
        Categoria categoriaAtualizada = categoriaInputPort.atualizar(categoria);
        CategoriaResponse statusPedidoResponse = categoriaMapper.toCategoriaResponse(categoriaAtualizada);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar categoria", description = "Deleta uma categoria de produto.")
    public ResponseEntity<Void> deletarCategoria(@PathVariable("id") final Long id) {
        categoriaInputPort.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar categoria por ID", description = "Retorna uma categoria de produto.")
    public ResponseEntity<CategoriaResponse> consultarCategoria(@PathVariable("id") final Long id) {
        Categoria categoria = categoriaInputPort.consultar(id);
        CategoriaResponse statusPedidoResponse = categoriaMapper.toCategoriaResponse(categoria);
        return ResponseEntity.ok().body(statusPedidoResponse);
    }

    @GetMapping("consultar-por-nome/{nome}")
    @Operation(summary = "Consultar categoria por nome", description = "Retorna uma categoria de produto por nome.")
    public ResponseEntity<CategoriaResponse> consultarCategoriaPorNome(@PathVariable("nome") final String nome) {
        Categoria categoria = categoriaInputPort.consultarPorNome(nome);
        CategoriaResponse categoriaResponse = categoriaMapper.toCategoriaResponse(categoria);
        return ResponseEntity.ok().body(categoriaResponse);
    }

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias de produtos.")
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        List<Categoria> categorias = categoriaInputPort.listar();
        List<CategoriaResponse> categoriasResponse = categoriaMapper.toCategoriaResponseList(categorias);
        return ResponseEntity.ok().body(categoriasResponse);
    }
}
