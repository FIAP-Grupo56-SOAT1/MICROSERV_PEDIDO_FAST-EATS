package br.com.fiap.fasteats.dataprovider.repository.mapper;

import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoEntity;
import br.com.fiap.fasteats.core.domain.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoEntityMapper {
    @Mapping(source = "categoria.id", target = "categoriaEntity.id")
    @Mapping(source = "categoria.id", target = "categoriaId")
    ProdutoEntity toProdutoEntity(Produto produto);

    @Mapping(source = "categoriaEntity.id", target = "categoria.id")
    //@Mapping(source = "categoriaId", target = "categoria.id")
    Produto toProduto(ProdutoEntity produtoEntity);
}


