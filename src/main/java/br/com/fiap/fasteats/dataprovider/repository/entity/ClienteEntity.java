package br.com.fiap.fasteats.dataprovider.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
@Table(name = "clientes")
@Entity(name = "Cliente")
public class ClienteEntity {

    @Id
    @Column(nullable = true, length = 11, name="cpf")
    private String cpf;

    @Column(nullable = true, length = 200,name = "primeironome")
    private String primeiroNome;

    @Column(nullable = true, length = 200, name = "ultimonome")
    private String ultimoNome;

    @Column(nullable = true, length = 200, name = "email")
    private String email;

    @Column(nullable = true,name = "ativo")
    private Boolean ativo = true;

    public String getNome() {
        return primeiroNome + " " + ultimoNome;
    }
}
