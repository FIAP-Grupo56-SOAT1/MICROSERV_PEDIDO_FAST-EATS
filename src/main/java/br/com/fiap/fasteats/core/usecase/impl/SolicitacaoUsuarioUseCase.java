package br.com.fiap.fasteats.core.usecase.impl;

import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.usecase.SolicitacaoUsuarioInputPort;

public class SolicitacaoUsuarioUseCase implements SolicitacaoUsuarioInputPort {
    private final SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort;
    private final SolicitacaoUsuarioInputPort solicitacaoUsuarioInputPort;

    public SolicitacaoUsuarioUseCase(SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort,
                                     SolicitacaoUsuarioInputPort solicitacaoUsuarioInputPort) {
        this.solicitacaoUsuarioOutputPort = solicitacaoUsuarioOutputPort;
        this.solicitacaoUsuarioInputPort = solicitacaoUsuarioInputPort;
    }


    @Override
    public SolicitacaoUsuario criar(SolicitacaoUsuario solicitacaoUsuario) {
        return null;
    }

}
