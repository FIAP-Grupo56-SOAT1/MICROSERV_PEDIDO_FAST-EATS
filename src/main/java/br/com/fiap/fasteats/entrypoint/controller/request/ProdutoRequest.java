package br.com.fiap.fasteats.entrypoint.controller.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {
    private String nome;

    private String descricao;

    private Double valor;

    private Long categoriaId;

    private String imagemBase64;

    private String imagemUrl;

    private Boolean ativo;
}
