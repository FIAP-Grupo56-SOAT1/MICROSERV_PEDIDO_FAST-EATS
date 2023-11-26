package br.com.fiap.fasteats.entrypoint.controller.mapper;

import br.com.fiap.fasteats.entrypoint.controller.request.ProdutoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.ProdutoResponse;
import br.com.fiap.fasteats.core.domain.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    @Mapping(source = "categoriaId", target = "categoria.id")
    @Mapping(target = "id", ignore = true)
    Produto toProduto(ProdutoRequest produto);

    ProdutoResponse toProdutoResponse(Produto produto);

    List<ProdutoResponse> toProdutoResponseList(List<Produto> produtoList);
}
