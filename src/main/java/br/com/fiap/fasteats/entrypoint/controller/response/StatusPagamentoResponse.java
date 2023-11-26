package br.com.fiap.fasteats.entrypoint.controller.response;

import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusPagamentoResponse {
    private Long id;
    private String nome;
    private Boolean ativo;

    public StatusPagamentoResponse(StatusPagamento statusPagamento) {
        this.id = statusPagamento.getId();
        this.nome = statusPagamento.getNome();
        this.ativo = statusPagamento.getAtivo();
    }
}
