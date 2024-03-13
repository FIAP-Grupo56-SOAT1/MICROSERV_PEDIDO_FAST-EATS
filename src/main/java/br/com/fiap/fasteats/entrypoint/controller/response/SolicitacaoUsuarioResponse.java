package br.com.fiap.fasteats.entrypoint.controller.response;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoUsuarioResponse {
    private String cpf;
}
