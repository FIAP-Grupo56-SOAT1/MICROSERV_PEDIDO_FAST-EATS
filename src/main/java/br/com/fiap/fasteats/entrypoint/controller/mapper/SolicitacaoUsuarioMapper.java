package br.com.fiap.fasteats.entrypoint.controller.mapper;

import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.entrypoint.controller.response.SolicitacaoUsuarioResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolicitacaoUsuarioMapper {
    SolicitacaoUsuario toSolicitacaoUsuario(SolicitacaoUsuarioResponse solicitacaoUsuarioRequest);
    SolicitacaoUsuarioResponse toSolicitacaoUsuarioResponse(SolicitacaoUsuario solicitacaoUsuarioRequest);
}
