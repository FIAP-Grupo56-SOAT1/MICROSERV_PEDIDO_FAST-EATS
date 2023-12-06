package br.com.fiap.fasteats.dataprovider.client.request;

import br.com.fiap.fasteats.entrypoint.controller.request.ClienteRequest;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPagamentoExternoRequest {
    private Double valor;
}
