package br.com.fiap.fasteats.dataprovider.repository.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ProdutoDeUmPedido")
@Table(name = "produtosdeumpedido")
public class ProdutoPedidoEntity {
    @Id
    @EmbeddedId
    @AttributeOverride(name = "idPedido", column = @Column(name = "pedidoid_fk", nullable = false))
    @AttributeOverride(name = "idProduto", column = @Column(name = "produtoid_fk", nullable = false))
    private ProdutoPedidoIdEntity id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedidoid_fk", insertable = false, updatable = false)
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produtoid_fk", insertable = false, updatable = false)
    private ProdutoEntity produto;

    @Column(nullable = true)
    private Integer quantidade;

    @Column(nullable = true)
    private Double valor;

    public ProdutoPedidoIdEntity getId() {
        return this.id;
    }

    public void setId(ProdutoPedidoIdEntity id) {
        this.id = id;
    }

    public String getNome() {
        return produto.getNome();
    }
}
