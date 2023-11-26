package br.com.fiap.fasteats.core.domain.model;

import java.util.Objects;

public class Produto {

    private Long id;

    private String nome;

    private String descricao;

    private Double valor;

    private Categoria categoria;

    private Boolean ativo;

    private String imagemBase64;

    private String imagemUrl;

    public Produto() {
    }

    public Produto(Long id, String nome, String descricao, Double valor, Categoria categoria, Boolean ativo, String imagemBase64, String imagemUrl) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.ativo = ativo;
        this.imagemBase64 = imagemBase64;
        this.imagemUrl = imagemUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) && Objects.equals(nome, produto.nome) && Objects.equals(descricao, produto.descricao) && Objects.equals(valor, produto.valor) && Objects.equals(categoria, produto.categoria) && Objects.equals(ativo, produto.ativo) && Objects.equals(imagemBase64, produto.imagemBase64) && Objects.equals(imagemUrl, produto.imagemUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, valor, categoria, ativo, imagemBase64, imagemUrl);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", categoria=" + categoria +
                ", ativo=" + ativo +
                ", imagemBase64='" + imagemBase64 + '\'' +
                ", imagemUrl='" + imagemUrl + '\'' +
                '}';
    }
}
