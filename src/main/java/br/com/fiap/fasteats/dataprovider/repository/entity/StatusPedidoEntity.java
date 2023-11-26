package br.com.fiap.fasteats.dataprovider.repository.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "StatusPedido")
@Table(name = "statuspedidos")
@EqualsAndHashCode(of = "id")
public class StatusPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String nome;

    @Column()
    private Boolean ativo = true;

}
