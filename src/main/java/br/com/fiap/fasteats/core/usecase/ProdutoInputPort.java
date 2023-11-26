package br.com.fiap.fasteats.core.usecase;

import br.com.fiap.fasteats.core.domain.model.Produto;

import java.util.List;

public interface ProdutoInputPort {

    Produto criar(Produto produto);

    Produto consultar(Long id);

    List<Produto> listar();

    Produto atualizar(Produto produto);

    void deletar(Long id);

    Produto consultarPorNome(String nome);

    List<Produto> consultarPorCategoria(Long categoriaId);
}
