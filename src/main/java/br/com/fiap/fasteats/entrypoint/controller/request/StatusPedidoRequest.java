package br.com.fiap.fasteats.entrypoint.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusPedidoRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private Boolean ativo;
}
