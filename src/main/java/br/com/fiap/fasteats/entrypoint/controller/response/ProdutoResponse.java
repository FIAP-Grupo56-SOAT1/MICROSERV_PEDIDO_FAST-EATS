package br.com.fiap.fasteats.entrypoint.controller.response;

import br.com.fiap.fasteats.core.domain.model.Categoria;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoResponse {
    private Long id;

    private String nome;

    private String descricao;

    private Double valor;

    private Categoria categoria;

    private String imagemBase64;

    private String imagemUrl;

    private Boolean ativo;
}
