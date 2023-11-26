package br.com.fiap.fasteats.entrypoint.controller.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
    private String cpf;

    private String primeiroNome;

    private String ultimoNome;

    private String email;

    private Boolean ativo;
}
