package br.com.fiap.fasteats.entrypoint.controller.mapper;


import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.entrypoint.controller.request.CategoriaRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.CategoriaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    @Mapping(target = "id", ignore = true)
    Categoria toCategoria(CategoriaRequest categoria);

    CategoriaResponse toCategoriaResponse(Categoria categoria);

    List<CategoriaResponse> toCategoriaResponseList(List<Categoria> categoriaList);
}
