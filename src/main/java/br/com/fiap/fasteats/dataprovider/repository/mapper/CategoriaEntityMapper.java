package br.com.fiap.fasteats.dataprovider.repository.mapper;

import br.com.fiap.fasteats.dataprovider.repository.entity.CategoriaEntity;
import br.com.fiap.fasteats.core.domain.model.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaEntityMapper {
    CategoriaEntity toCategoriaEntity(Categoria categoria);
    Categoria toCategoria(CategoriaEntity categoriaEntity);
}
