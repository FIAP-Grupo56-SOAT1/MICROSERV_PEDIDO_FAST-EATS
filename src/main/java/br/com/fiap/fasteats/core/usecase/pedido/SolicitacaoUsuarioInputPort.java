package br.com.fiap.fasteats.core.usecase.pedido;

import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;

public interface SolicitacaoUsuarioInputPort {
    SolicitacaoUsuario criar(SolicitacaoUsuario solicitacaoUsuario);
}
