package br.com.fiap.fasteats.core.usecase;

import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;

public interface SolicitacaoUsuarioInputPort {
    SolicitacaoUsuario criar(SolicitacaoUsuario solicitacaoUsuario);
}
