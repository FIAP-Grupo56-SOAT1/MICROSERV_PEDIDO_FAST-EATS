package br.com.fiap.fasteats.dataprovider.client.response;

import br.com.fiap.fasteats.entrypoint.controller.response.ClienteResponse;
import br.com.fiap.fasteats.entrypoint.controller.response.FormaPagamentoResponse;
import br.com.fiap.fasteats.entrypoint.controller.response.PedidoResponse;
import br.com.fiap.fasteats.entrypoint.controller.response.StatusPagamentoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoResponse {
    private Long id;
    private FormaPagamentoResponse formaPagamento;
    private StatusPagamentoResponse statusPagamento;
    private PedidoResponse pedido;
    private ClienteResponse cliente;
    private Long idPagamentoExterno;
    private String qrCode;
    private String urlPagamento;
    private LocalDateTime dataHoraCriado;
    private LocalDateTime dataHoraFinalizado;
}
