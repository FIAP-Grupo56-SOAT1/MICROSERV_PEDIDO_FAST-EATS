package br.com.fiap.fasteats.dataprovider.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable

public class ProdutoPedidoIdEntity implements java.io.Serializable {

    @Column(name = "pedidoid_fk", nullable = false)
    private Long idPedido;

    @Column(name = "produtoid_fk", nullable = false)
    private Long idProduto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoPedidoIdEntity that = (ProdutoPedidoIdEntity) o;
        return Objects.equals(idPedido, that.idPedido) && Objects.equals(idProduto, that.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idProduto);
    }
}
