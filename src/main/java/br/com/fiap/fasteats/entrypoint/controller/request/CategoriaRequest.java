package br.com.fiap.fasteats.entrypoint.controller.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {
    private String nome;
    private String descricao;
    private Boolean ativo;
}
