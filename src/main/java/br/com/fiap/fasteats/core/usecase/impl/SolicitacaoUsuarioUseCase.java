package br.com.fiap.fasteats.core.usecase.impl;

import br.com.fiap.fasteats.core.constants.SolicitacaoUsuarioConstants;
import br.com.fiap.fasteats.core.dataprovider.ClienteOutputPort;
import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ClienteNotFound;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.domain.valueobject.Cpf;
import br.com.fiap.fasteats.core.usecase.SolicitacaoUsuarioInputPort;

import java.time.LocalDateTime;

public class SolicitacaoUsuarioUseCase implements SolicitacaoUsuarioInputPort {
    private final SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort;
    private final ClienteOutputPort clienteOutputPort;


    public SolicitacaoUsuarioUseCase(SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort, ClienteOutputPort clienteOutputPort) {
        this.solicitacaoUsuarioOutputPort = solicitacaoUsuarioOutputPort;

        this.clienteOutputPort = clienteOutputPort;
    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoDesativarUsuario(SolicitacaoUsuario solicitacaoUsuario) {

        var cliente = buscarClientePorCpf(solicitacaoUsuario.getCpf());


        solicitacaoUsuario.setDataHoraSolicitacao(LocalDateTime.now());
        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_DESATIVAR);

        salvar(solicitacaoUsuario);

        return null;
    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoExcluirUsuario(SolicitacaoUsuario solicitacaoUsuario) {

        var cliente = buscarClientePorCpf(solicitacaoUsuario.getCpf());
        solicitacaoUsuario.setDataHoraSolicitacao(LocalDateTime.now());
        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_EXCLUIR);

        salvar(solicitacaoUsuario);

        return null;
    }


    private SolicitacaoUsuario salvar(SolicitacaoUsuario solicitacaoUsuario){
        return solicitacaoUsuarioOutputPort.salvar(solicitacaoUsuario);
    }

    private Cliente buscarClientePorCpf(String cpf){
        String cpfFormatado = formatarEValidarCpf(cpf);
        return clienteOutputPort.consultarCliente(cpf).orElseThrow(() -> new ClienteNotFound("Cliente não encontrado cpf " + cpfFormatado));
    }

    private String formatarEValidarCpf(String cpf) {
        Cpf cpfFormatado = new Cpf(cpf);
        return cpfFormatado.valor();
    }
}
