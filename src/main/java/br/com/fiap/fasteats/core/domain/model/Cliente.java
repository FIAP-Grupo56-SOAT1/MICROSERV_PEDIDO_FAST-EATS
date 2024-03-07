package br.com.fiap.fasteats.core.domain.model;
import java.util.Objects;


public class Cliente {

    private Long id;
    private String cpf;
    private String primeiroNome;
    private String ultimoNome;
    private String email;
    private Boolean ativo;

    public Cliente() {
    }

    public Cliente(Long id, String cpf, String primeiroNome, String ultimoNome, String email, Boolean ativo) {
        this.id  = id;
        this.cpf = cpf;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
        this.ativo = ativo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf) && Objects.equals(primeiroNome, cliente.primeiroNome) && Objects.equals(ultimoNome, cliente.ultimoNome) && Objects.equals(email, cliente.email) && Objects.equals(ativo, cliente.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, primeiroNome, ultimoNome, email, ativo);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", primeiroNome='" + primeiroNome + '\'' +
                ", ultimoNome='" + ultimoNome + '\'' +
                ", email='" + email + '\'' +
                ", ativo=" + ativo +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
