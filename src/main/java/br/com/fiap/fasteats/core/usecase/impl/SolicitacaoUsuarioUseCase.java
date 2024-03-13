package br.com.fiap.fasteats.core.usecase.impl;

import br.com.fiap.fasteats.core.constants.SolicitacaoUsuarioConstants;
import br.com.fiap.fasteats.core.dataprovider.CriptografiaOutputPort;
import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;
import br.com.fiap.fasteats.core.usecase.SolicitacaoUsuarioInputPort;

import java.time.LocalDateTime;

public class SolicitacaoUsuarioUseCase implements SolicitacaoUsuarioInputPort {
    private final SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort;
    private final ClienteInputPort clienteInputPort;
    private final CriptografiaOutputPort criptografiaOutputPort;


    public SolicitacaoUsuarioUseCase(SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort,
                                     ClienteInputPort clienteInputPort,
                                     CriptografiaOutputPort criptografiaOutputPort) {
        this.solicitacaoUsuarioOutputPort = solicitacaoUsuarioOutputPort;
        this.clienteInputPort = clienteInputPort;
        this.criptografiaOutputPort = criptografiaOutputPort;
    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoDesativarUsuario(SolicitacaoUsuario solicitacaoUsuario) {
        Cliente cliente  = clienteInputPort.consultar(solicitacaoUsuario.getCpf());
        String cpfCriptografado = criptografiaOutputPort.criptografar(solicitacaoUsuario.getCpf());
        solicitacaoUsuario.setCpf(cpfCriptografado);
        solicitacaoUsuario.setDataHoraSolicitacao(LocalDateTime.now());
        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_DESATIVAR);
        solicitacaoUsuario.setDataHoraExecucao(LocalDateTime.now());
        clienteInputPort.desativarCliente(cliente.getCpf());
        return solicitacaoUsuarioOutputPort.salvar(solicitacaoUsuario);
    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoExcluirUsuario(SolicitacaoUsuario solicitacaoUsuario) {
        Cliente cliente = clienteInputPort.consultar(solicitacaoUsuario.getCpf());
        String cpfCriptografado = criptografiaOutputPort.criptografar(solicitacaoUsuario.getCpf());
        solicitacaoUsuario.setCpf(cpfCriptografado);
        solicitacaoUsuario.setDataHoraSolicitacao(LocalDateTime.now());
        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_EXCLUIR);
        solicitacaoUsuario.setDataHoraExecucao(LocalDateTime.now());
        clienteInputPort.excluirClienteLgpd(cliente.getCpf());
        return solicitacaoUsuarioOutputPort.salvar(solicitacaoUsuario);
    }
}
