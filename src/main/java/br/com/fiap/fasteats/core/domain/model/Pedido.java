package br.com.fiap.fasteats.core.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {
    private Long id;
    private Cliente cliente;
    private boolean identificaCliente;
    private Long statusPedido;
    private String nomeStatusPedido;
    private Boolean ativo;
    private LocalDateTime dataHoraCriado;
    private LocalDateTime dataHoraRecebimento;
    private LocalDateTime dataHoraFinalizado;
    private String tempoEspera;
    private Double valor;
    private List<ProdutoPedido> produtos = new ArrayList<>();
    private Long idPagamentoExterno;
    private String qrCode;
    private String urlPagamento;

    public Pedido() {
    }

    public Pedido(Long id,
            Cliente cliente,
            boolean identificaCliente,
            Long statusPedido,
            String nomeStatusPedido,
            Boolean ativo,
            LocalDateTime dataHoraCriado,
            LocalDateTime dataHoraRecebimento,
            LocalDateTime dataHoraFinalizado,
            String tempoEspera,
            Double valor,
            List<ProdutoPedido> produtos,
            Long idPagamentoExterno,
            String qrCode,
            String urlPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.identificaCliente = identificaCliente;
        this.statusPedido = statusPedido;
        this.nomeStatusPedido = nomeStatusPedido;
        this.ativo = ativo;
        this.dataHoraCriado = dataHoraCriado;
        this.dataHoraRecebimento = dataHoraRecebimento;
        this.dataHoraFinalizado = dataHoraFinalizado;
        this.valor = valor;
        this.produtos = produtos;
        this.tempoEspera = tempoEspera;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isIdentificaCliente() {
        return identificaCliente;
    }

    public void setIdentificaCliente(boolean identificaCliente) {
        this.identificaCliente = identificaCliente;
    }

    public Long getStatusPedido() {
        return statusPedido;
    }

    public String getNomeStatusPedido() {
        return nomeStatusPedido;
    }

    public void setNomeStatusPedido(String nomeStatusPedido) {
        this.nomeStatusPedido = nomeStatusPedido;
    }

    public void setStatusPedido(Long statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataHoraCriado() {
        return dataHoraCriado;
    }

    public void setDataHoraCriado(LocalDateTime dataHoraCriado) {
        this.dataHoraCriado = dataHoraCriado;
    }

    public LocalDateTime getDataHoraRecebimento() {
        return dataHoraRecebimento;
    }

    public void setDataHoraRecebimento(LocalDateTime dataHoraRecebimento) {
        this.dataHoraRecebimento = dataHoraRecebimento;
    }

    public LocalDateTime getDataHoraFinalizado() {
        return dataHoraFinalizado;
    }

    public void setDataHoraFinalizado(LocalDateTime dataHoraFinalizado) {
        this.dataHoraFinalizado = dataHoraFinalizado;
    }

    public String getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(String tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<ProdutoPedido> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoPedido> produtos) {
        this.produtos = produtos;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pedido pedido = (Pedido) o;
        return identificaCliente == pedido.identificaCliente && Objects.equals(id, pedido.id)
                && Objects.equals(cliente, pedido.cliente) && Objects.equals(statusPedido, pedido.statusPedido)
                && Objects.equals(nomeStatusPedido, pedido.nomeStatusPedido) && Objects.equals(ativo, pedido.ativo)
                && Objects.equals(dataHoraCriado, pedido.dataHoraCriado)
                && Objects.equals(dataHoraRecebimento, pedido.dataHoraRecebimento)
                && Objects.equals(dataHoraFinalizado, pedido.dataHoraFinalizado)
                && Objects.equals(tempoEspera, pedido.tempoEspera) && Objects.equals(valor, pedido.valor)
                && Objects.equals(produtos, pedido.produtos)
                && Objects.equals(idPagamentoExterno, pedido.idPagamentoExterno)
                && Objects.equals(qrCode, pedido.qrCode) && Objects.equals(urlPagamento, pedido.urlPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, identificaCliente, statusPedido, nomeStatusPedido, ativo, dataHoraCriado,
                dataHoraRecebimento, dataHoraFinalizado, tempoEspera, valor, produtos, idPagamentoExterno, qrCode,
                urlPagamento);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", identificaCliente=" + identificaCliente +
                ", statusPedido=" + statusPedido +
                ", nomeStatusPedido='" + nomeStatusPedido + '\'' +
                ", ativo=" + ativo +
                ", dataHoraCriado=" + dataHoraCriado +
                ", dataHoraRecebimento=" + dataHoraRecebimento +
                ", dataHoraFinalizado=" + dataHoraFinalizado +
                ", tempoEspera='" + tempoEspera + '\'' +
                ", valor=" + valor +
                ", produtos=" + produtos +
                ", idPagamentoExterno=" + idPagamentoExterno +
                ", qrCode='" + qrCode + '\'' +
                ", urlPagamento='" + urlPagamento + '\'' +
                '}';
    }
}
