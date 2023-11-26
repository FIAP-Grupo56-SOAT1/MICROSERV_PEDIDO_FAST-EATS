package br.com.fiap.fasteats.core.usecase;

import br.com.fiap.fasteats.core.domain.model.Categoria;

import java.util.List;

public interface CategoriaInputPort {
    Categoria criar(Categoria categoria);

    Categoria consultar(Long id);

    Categoria atualizar(Categoria categoria);

    void deletar(Long id);

    List<Categoria> listar();

    Categoria consultarPorNome(String nome);
}
