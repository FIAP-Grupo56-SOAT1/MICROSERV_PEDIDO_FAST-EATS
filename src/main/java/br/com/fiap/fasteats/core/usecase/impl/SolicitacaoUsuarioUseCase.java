package br.com.fiap.fasteats.core.usecase.impl;

import br.com.fiap.fasteats.core.constants.SolicitacaoUsuarioConstants;
import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.usecase.SolicitacaoUsuarioInputPort;

public class SolicitacaoUsuarioUseCase implements SolicitacaoUsuarioInputPort {
    private final SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort;


    public SolicitacaoUsuarioUseCase(SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort) {
        this.solicitacaoUsuarioOutputPort = solicitacaoUsuarioOutputPort;

    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoDesativarUsuario(SolicitacaoUsuario solicitacaoUsuario) {

        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_DESATIVAR);

        salvar(solicitacaoUsuario);

        return null;
    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoExcluirUsuario(SolicitacaoUsuario solicitacaoUsuario) {

        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_DESATIVAR);

        salvar(solicitacaoUsuario);

        return null;
    }


    private SolicitacaoUsuario salvar(SolicitacaoUsuario solicitacaoUsuario){
        return solicitacaoUsuarioOutputPort.salvar(solicitacaoUsuario);
    }
}
