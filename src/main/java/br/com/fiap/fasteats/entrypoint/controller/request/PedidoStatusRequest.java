package br.com.fiap.fasteats.entrypoint.controller.request;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoStatusRequest {
   public Long idStatusPedido;
   public LocalDateTime dataHoraRecebimento;
   public LocalDateTime dataHoraFinalizado;
}
