package br.com.fiap.fasteats.entrypoint.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class GerenciarPedidoResponse {
    private Long id;
    private ClienteResponse cliente;
    private String statusPedido;
    private LocalDateTime dataHoraCriado;
    private LocalDateTime dataHoraRecebimento;
    private LocalDateTime dataHoraFinalizado;
    private double valor;
    private String tempoEspera;
    private List<ProdutoPedidoResponse> produtos = new ArrayList<>();
}
