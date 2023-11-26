package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.dataprovider.repository.ProdutoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.ProdutoEntityMapper;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.dataprovider.ProdutoOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ProdutoAdapter implements ProdutoOutputPort {
    private final ProdutoRepository produtoRepository;
    private final ProdutoEntityMapper produtoEntityMapper;

    @Override
    public Produto criar(Produto produto) {
        var produtoEntity = produtoEntityMapper.toProdutoEntity(produto);
        var produtoEntitySalvo = produtoRepository.save(produtoEntity);
        return produtoEntityMapper.toProduto(produtoEntitySalvo);
    }

    @Override
    public Optional<Produto> consultar(Long id) {
        Optional<ProdutoEntity> produtoEntity = produtoRepository.findById(id);

        if (produtoEntity.isEmpty()) {
            return Optional.empty();
        }

        Produto produto = produtoEntityMapper.toProduto(produtoEntity.get());
        return Optional.of(produto);
    }

    @Override
    public Optional<List<Produto>> listar() {
        var produtosEntity = produtoRepository.findAll();
        var produtos = produtosEntity.stream()
                .map(produtoEntityMapper::toProduto)
                .toList();
        return Optional.of(produtos);
    }

    @Override
    public Produto atualizar(Produto produto) {
        var produtoEntity = produtoEntityMapper.toProdutoEntity(produto);
        var produtoEntityAtualizado = produtoRepository.save(produtoEntity);
        return produtoEntityMapper.toProduto(produtoEntityAtualizado);
    }

    @Override
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Optional<Produto> consultarPorNome(String nome) {
        var produtosEntity = produtoRepository.findByNome(nome);
        var produtos = produtosEntity.stream()
                .map(produtoEntityMapper::toProduto)
                .toList();
        return produtos.stream().findFirst();
    }

    @Override
    public Optional<List<Produto>> consultarPorCategoria(Long categoriaId) {
        var produtosEntity = produtoRepository.findByCategoria(categoriaId);
        var produtos = produtosEntity.stream()
                .map(produtoEntityMapper::toProduto)
                .toList();
        return Optional.of(produtos);
    }
}
