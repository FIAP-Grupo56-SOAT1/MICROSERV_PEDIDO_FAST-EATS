package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.usecase.pedido.SolicitacaoUsuarioInputPort;

public class SolicitacaoUsuarioUseCase implements SolicitacaoUsuarioInputPort {

    private final SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort;

    public SolicitacaoUsuarioUseCase(SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort) {
        this.solicitacaoUsuarioOutputPort = solicitacaoUsuarioOutputPort;
    }

    @Override
    public SolicitacaoUsuario criar(SolicitacaoUsuario solicitacaoUsuario) {
        return null;
    }
}
