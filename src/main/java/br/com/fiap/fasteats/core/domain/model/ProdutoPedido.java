package br.com.fiap.fasteats.core.domain.model;

import java.util.Objects;

public class ProdutoPedido {
    private Long idPedido;

    private Long idProduto;

    private String nomeProduto;

    private String descricaoProduto;

    private Integer quantidade;

    private Double valor;

    public ProdutoPedido() {
    }

    public ProdutoPedido(Long idPedido, Long idProduto, String nomeProduto, String descricaoProduto, Integer quantidade, Double valor) {
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoPedido that = (ProdutoPedido) o;
        return Objects.equals(idPedido, that.idPedido) && Objects.equals(idProduto, that.idProduto) && Objects.equals(nomeProduto, that.nomeProduto) && Objects.equals(descricaoProduto, that.descricaoProduto) && Objects.equals(quantidade, that.quantidade) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idProduto, nomeProduto, descricaoProduto, quantidade, valor);
    }
}
