package br.com.fiap.fasteats.core.domain.model;
import java.time.LocalDateTime;
import java.util.Objects;


public class SolicitacaoUsuario {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public LocalDateTime getDataHoraExecucao() {
        return dataHoraExecucao;
    }

    public void setDataHoraExecucao(LocalDateTime dataHoraExecucao) {
        this.dataHoraExecucao = dataHoraExecucao;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    private String cpf;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraExecucao;
    private String operacao;

    public SolicitacaoUsuario(Long id, String cpf, LocalDateTime dataHoraSolicitacao, LocalDateTime dataHoraExecucao, String operacao) {
        this.id = id;
        this.cpf = cpf;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.dataHoraExecucao = dataHoraExecucao;
        this.operacao = operacao;
    }
}
