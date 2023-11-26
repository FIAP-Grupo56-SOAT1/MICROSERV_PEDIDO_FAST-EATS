package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.dataprovider.repository.CategoriaRepository;
import br.com.fiap.fasteats.dataprovider.repository.mapper.CategoriaEntityMapper;
import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.dataprovider.CategoriaOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoriaAdapter implements CategoriaOutputPort {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaEntityMapper categoriaEntityMapper;

    @Override
    public Categoria criar(Categoria categoria) {
        var categoriaEntity = categoriaEntityMapper.toCategoriaEntity(categoria);
        var categoriaEntitySalvo = categoriaRepository.save(categoriaEntity);
        return categoriaEntityMapper.toCategoria(categoriaEntitySalvo);
    }

    @Override
    public Optional<Categoria> consultar(Long id) {
        return categoriaRepository.findById(id).map(categoriaEntityMapper::toCategoria);
    }

    @Override
    public Categoria atualizar(Categoria categoria) {
        var categoriaEntity = categoriaEntityMapper.toCategoriaEntity(categoria);
        var categoriaEntityAtualizado = categoriaRepository.save(categoriaEntity);
        return categoriaEntityMapper.toCategoria(categoriaEntityAtualizado);
    }

    @Override
    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public Optional<List<Categoria>> listar() {
        var categoriaEntities = categoriaRepository.findAll();
        var categorias = categoriaEntities.stream()
                .map(categoriaEntityMapper::toCategoria)
                .toList();
        return Optional.of(categorias);
    }

    @Override
    public Optional<Categoria> consultarPorNome(String nome) {
        var categoriaEntities = categoriaRepository.findByNome(nome.toUpperCase());
        var categorias = categoriaEntities.stream()
                .map(categoriaEntityMapper::toCategoria)
                .toList();
        return categorias.stream().findFirst();
    }
}
