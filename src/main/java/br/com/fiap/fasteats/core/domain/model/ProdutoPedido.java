package br.com.fiap.fasteats.core.domain.model;

import java.util.Objects;

public class ProdutoPedido {
    private Long idPedido;

    private Long idProduto;

    private String nomeProduto;

    private String descricaoProduto;

    private int quantidade;

    private double valor;

    public ProdutoPedido() {
    }

    public ProdutoPedido(Long idPedido, Long idProduto, String nomeProduto, String descricaoProduto, int quantidade, double valor) {
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdProduto() {
        return this.idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return this.descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }


    public Integer getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return this.valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ProdutoPedido idPedido(Long idPedido) {
        setIdPedido(idPedido);
        return this;
    }

    public ProdutoPedido idProduto(Long idProduto) {
        setIdProduto(idProduto);
        return this;
    }

    public ProdutoPedido nomeProduto(String nomeProduto) {
        setNomeProduto(nomeProduto);
        return this;
    }

    public ProdutoPedido descricaoProduto(String descricaoProduto) {
        setDescricaoProduto(descricaoProduto);
        return this;
    }

    public ProdutoPedido quantidade(Integer quantidade) {
        setQuantidade(quantidade);
        return this;
    }

    public ProdutoPedido valor(Double valor) {
        setValor(valor);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProdutoPedido produtoPedido)) {
            return false;
        }
        return Objects.equals(idPedido, produtoPedido.idPedido) && Objects.equals(idProduto, produtoPedido.idProduto) && Objects.equals(nomeProduto, produtoPedido.nomeProduto) && Objects.equals(descricaoProduto, produtoPedido.descricaoProduto) && Objects.equals(quantidade, produtoPedido.quantidade) && Objects.equals(valor, produtoPedido.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idProduto, nomeProduto, descricaoProduto, quantidade, valor);
    }

    @Override
    public String toString() {
        return "{" +
                ", idPedido='" + getIdPedido() + "'" +
                ", idProduto='" + getIdProduto() + "'" +
                ", nomeProduto='" + getNomeProduto() + "'" +
                ", descricaoProduto='" + getDescricaoProduto() + "'" +
                ", quantidade='" + getQuantidade() + "'" +
                ", valor='" + getValor() + "'" +
                "}";
    }

}
