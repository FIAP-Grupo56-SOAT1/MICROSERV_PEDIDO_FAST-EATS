package br.com.fiap.fasteats.entrypoint.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoComprovantePagamento {
    private String nome;
    private double valor;
    private int quantidade;
}
