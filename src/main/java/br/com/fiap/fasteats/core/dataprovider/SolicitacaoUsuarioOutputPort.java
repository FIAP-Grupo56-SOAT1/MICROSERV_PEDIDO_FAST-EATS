package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;

public interface SolicitacaoUsuarioOutputPort {
    SolicitacaoUsuario salvar(SolicitacaoUsuario solicitacaoUsuario);
}
