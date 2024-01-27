package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.ProdutoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.dataprovider.repository.ProdutoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.ProdutoEntityMapper;
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
        return salvar(produto);
    }

    @Override
    public Optional<Produto> consultar(Long id) {
        return produtoRepository.findById(id).map(produtoEntityMapper::toProduto);
    }

    @Override
    public Optional<List<Produto>> listar() {
        return Optional.of(produtoRepository.findAll().stream().map(produtoEntityMapper::toProduto).toList());
    }

    @Override
    public Produto atualizar(Produto produto) {
        return salvar(produto);
    }

    @Override
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Optional<Produto> consultarPorNome(String nome) {
        return produtoRepository.findByNome(nome).stream().findFirst().map(produtoEntityMapper::toProduto);
    }

    @Override
    public Optional<List<Produto>> consultarPorCategoria(Long categoriaId) {
        return Optional.of(produtoRepository.findByCategoria(categoriaId)
                .stream().map(produtoEntityMapper::toProduto).toList());
    }

    private Produto salvar(Produto produto) {
        ProdutoEntity produtoEntity = produtoEntityMapper.toProdutoEntity(produto);
        return produtoEntityMapper.toProduto(produtoRepository.save(produtoEntity));
    }
}
