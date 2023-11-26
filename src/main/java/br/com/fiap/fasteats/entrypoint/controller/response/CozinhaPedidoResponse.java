package br.com.fiap.fasteats.entrypoint.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CozinhaPedidoResponse {
    private Long id;

    private Long statusId;

    private String statusNome;
}
