package br.com.fiap.fasteats.core.domain.model;

import java.time.LocalDateTime;

public class Pagamento {
    private Long id;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
    private Pedido pedido;
    private LocalDateTime dataHoraCriado;
    private LocalDateTime dataHoraProcessamento;
    private LocalDateTime dataHoraFinalizado;
    private Long idPagamentoExterno;
    private String qrCode;
    private String urlPagamento;

    public Pagamento() {
    }

    public Pagamento(Long id,
                     FormaPagamento formaPagamento,
                     StatusPagamento statusPagamento,
                     Pedido pedido,
                     LocalDateTime dataHoraCriado,
                     LocalDateTime dataHoraProcessamento,
                     LocalDateTime dataHoraFinalizado,
                     Long idPagamentoExterno,
                     String qrCode,
                     String urlPagamento) {
        this.id = id;
        this.formaPagamento = formaPagamento;
        this.statusPagamento = statusPagamento;
        this.pedido = pedido;
        this.dataHoraCriado = dataHoraCriado;
        this.dataHoraProcessamento = dataHoraProcessamento;
        this.dataHoraFinalizado = dataHoraFinalizado;
        this.idPagamentoExterno = idPagamentoExterno;
        this.qrCode = qrCode;
        this.urlPagamento = urlPagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDateTime getDataHoraCriado() {
        return dataHoraCriado;
    }

    public void setDataHoraCriado(LocalDateTime dataHoraCriado) {
        this.dataHoraCriado = dataHoraCriado;
    }

    public LocalDateTime getDataHoraProcessamento() {
        return dataHoraProcessamento;
    }

    public void setDataHoraProcessamento(LocalDateTime dataHoraProcessamento) {
        this.dataHoraProcessamento = dataHoraProcessamento;
    }

    public LocalDateTime getDataHoraFinalizado() {
        return dataHoraFinalizado;
    }

    public void setDataHoraFinalizado(LocalDateTime dataHoraFinalizado) {
        this.dataHoraFinalizado = dataHoraFinalizado;
    }

    public Long getIdPagamentoExterno() {
        return idPagamentoExterno;
    }

    public void setIdPagamentoExterno(Long idPagamentoExterno) {
        this.idPagamentoExterno = idPagamentoExterno;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUrlPagamento() {
        return urlPagamento;
    }

    public void setUrlPagamento(String urlPagamento) {
        this.urlPagamento = urlPagamento;
    }
}
