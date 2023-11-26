package br.com.fiap.fasteats.entrypoint.controller.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {
    private ClienteRequest cliente;

    private boolean identificaCliente;
}
