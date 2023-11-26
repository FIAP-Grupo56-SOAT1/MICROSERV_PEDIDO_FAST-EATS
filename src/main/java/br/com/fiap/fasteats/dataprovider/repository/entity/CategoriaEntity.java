package br.com.fiap.fasteats.dataprovider.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Categoria")
@Table(name = "categorias")
@EqualsAndHashCode(of = "id")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 200)
    private String nome;

    @Column(length = 400)
    private String descricao;

    @Column(nullable = true)
    private Boolean ativo;
}
