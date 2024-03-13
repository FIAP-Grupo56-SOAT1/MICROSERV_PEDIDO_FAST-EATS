package br.com.fiap.fasteats.dataprovider.repository.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "solicitacaodousuario")
@Table(name = "solicitacaodousuario")
public class SolicitacaoUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 11, name = "cpf")
    private String cpf;

    @Column(name = "datahorasolicitacao")
    private LocalDateTime dataHoraSolicitacao;

    @Column(name = "datahoraexecucao")
    private LocalDateTime dataHoraExecucao;

    @Column(name = "operacao")
    private String operacao;
}
