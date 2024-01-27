package br.com.fiap.fasteats.dataprovider.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clienteid_fk")
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statuspedidoid_fk")
    private StatusPedidoEntity statusEntity;

    @Column(name = "datahoracriado")
    private LocalDateTime dataHoraCriado;

    @Column(name = "datahorarecebimento")
    private LocalDateTime dataHoraRecebimento;

    @Column(name = "datahorafinalizado")
    private LocalDateTime dataHoraFinalizado;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(nullable = true)
    private Double valor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ProdutoPedidoEntity> listaProdutos = new ArrayList<>();
}
