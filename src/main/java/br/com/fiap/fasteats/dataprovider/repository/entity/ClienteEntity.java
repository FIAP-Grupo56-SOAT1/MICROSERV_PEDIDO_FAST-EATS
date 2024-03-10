package br.com.fiap.fasteats.dataprovider.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "clientes")
@Entity(name = "clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
