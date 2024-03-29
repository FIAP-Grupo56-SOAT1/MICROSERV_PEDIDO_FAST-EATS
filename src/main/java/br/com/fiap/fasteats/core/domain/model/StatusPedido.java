package br.com.fiap.fasteats.core.domain.model;

import java.util.Objects;

public class StatusPedido {
    private Long id;
    private String nome;
    private Boolean ativo;

    public StatusPedido() {
    }

    public StatusPedido(Long id, String nome, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusPedido that = (StatusPedido) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(ativo, that.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, ativo);
    }

    @Override
    public String toString() {
        return "StatusPedido{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
