package br.com.fiap.fasteats.dataprovider.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Produto")
@Table(name = "produtos")
@EqualsAndHashCode(of = "id")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String nome;

    @Column(length = 400)
    private String descricao;

    @Column(length = 200)
    private Double valor;

    @Column(name = "categoriaid_fk")
    private Long categoriaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoriaid_fk", insertable = false, updatable = false)
    private CategoriaEntity categoriaEntity;

    @Column()
    private Boolean ativo = true;

    @Column(name = "imagembase64")
    private String imagemBase64;

    @Column(name = "imagemurl", length = 400)
    private String imagemUrl;
}
