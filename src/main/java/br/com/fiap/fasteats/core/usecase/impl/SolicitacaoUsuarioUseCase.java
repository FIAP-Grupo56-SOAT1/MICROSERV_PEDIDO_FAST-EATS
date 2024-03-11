package br.com.fiap.fasteats.core.usecase.impl;

import br.com.fiap.fasteats.core.constants.SolicitacaoUsuarioConstants;
import br.com.fiap.fasteats.core.dataprovider.ClienteOutputPort;
import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ClienteNotFound;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.domain.valueobject.Cpf;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;
import br.com.fiap.fasteats.core.usecase.SolicitacaoUsuarioInputPort;

import java.time.LocalDateTime;

public class SolicitacaoUsuarioUseCase implements SolicitacaoUsuarioInputPort {
    private final SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort;
    private final ClienteOutputPort clienteOutputPort;

    private final ClienteInputPort clienteInputPort;


    public SolicitacaoUsuarioUseCase(SolicitacaoUsuarioOutputPort solicitacaoUsuarioOutputPort, ClienteOutputPort clienteOutputPort, ClienteInputPort clienteInputPort) {
        this.solicitacaoUsuarioOutputPort = solicitacaoUsuarioOutputPort;

        this.clienteOutputPort = clienteOutputPort;
        this.clienteInputPort = clienteInputPort;
    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoDesativarUsuario(SolicitacaoUsuario solicitacaoUsuario) {

        Cliente cliente  = buscarClientePorCpf(solicitacaoUsuario.getCpf());
        solicitacaoUsuario.setDataHoraSolicitacao(LocalDateTime.now());
        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_DESATIVAR);
        solicitacaoUsuario.setDataHoraExecucao(LocalDateTime.now());

        clienteInputPort.desativarCliente(cliente);

        return salvar(solicitacaoUsuario);
    }

    @Override
    public SolicitacaoUsuario criarSolicitacaoExcluirUsuario(SolicitacaoUsuario solicitacaoUsuario) {

        Cliente cliente = buscarClientePorCpf(solicitacaoUsuario.getCpf());
        solicitacaoUsuario.setDataHoraSolicitacao(LocalDateTime.now());
        solicitacaoUsuario.setOperacao(SolicitacaoUsuarioConstants.OPERACAO_EXCLUIR);
        solicitacaoUsuario.setDataHoraExecucao(LocalDateTime.now());


        clienteInputPort.excluirClienteLgpd(cliente);

        return salvar(solicitacaoUsuario);
    }


    private SolicitacaoUsuario salvar(SolicitacaoUsuario solicitacaoUsuario){
        try {
            return solicitacaoUsuarioOutputPort.salvar(solicitacaoUsuario);
        } catch (Exception ex) {
            return null;
        }
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
