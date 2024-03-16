package br.com.fiap.fasteats.dataprovider.repository.mapper;

import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.dataprovider.repository.entity.SolicitacaoUsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolicitacaoUsuarioEntityMapper {
    SolicitacaoUsuarioEntity toSolicitacaoUsuarioEntity(SolicitacaoUsuario solicitacaoUsuario);

    SolicitacaoUsuario toSolicitacaoUsuario(SolicitacaoUsuarioEntity solicitacaoUsuarioEntity);
}
