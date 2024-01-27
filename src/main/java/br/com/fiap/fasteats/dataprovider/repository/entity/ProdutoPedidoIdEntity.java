package br.com.fiap.fasteats.dataprovider.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ProdutoPedidoIdEntity implements java.io.Serializable {
    @Column(name = "pedidoid_fk", nullable = false)
    private Long idPedido;

    @Column(name = "produtoid_fk", nullable = false)
    private Long idProduto;
}
