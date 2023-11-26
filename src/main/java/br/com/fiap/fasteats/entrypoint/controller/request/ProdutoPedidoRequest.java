package br.com.fiap.fasteats.entrypoint.controller.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoPedidoRequest {
    private Long idProduto;

    private int quantidade;
}
