package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.usecase.pedido.SolicitacaoUsuarioInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.SolicitacaoUsuarioMapper;
import br.com.fiap.fasteats.entrypoint.controller.response.SolicitacaoUsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("solicitacaoUsuario")
@RequiredArgsConstructor
@Tag(name = "solicitacaoUsuario", description = "Controller que gerencia o processo de criação de um solicitação do usuário")
public class SolicitacaoUsuarioController {
    private final SolicitacaoUsuarioInputPort solicitacaoUsuarioInputPort;
    private final SolicitacaoUsuarioMapper solicitacaoUsuarioMapper;

    @PostMapping
    @Operation(summary = "Identificação do cliente e criar pedido", description = "Identifica ou não o cliente e cria um novo pedido.")
    public ResponseEntity<SolicitacaoUsuarioResponse> criarPedido(@Valid @RequestBody SolicitacaoUsuarioResponse solicitacaoRequest) {
        SolicitacaoUsuario solicitacaoUsuario = solicitacaoUsuarioMapper.toSolicitacaoUsuario(solicitacaoRequest);
        SolicitacaoUsuario solicitacaoUsuarioCriado = solicitacaoUsuarioInputPort.criar(pedido);
        SolicitacaoUsuarioResponse solicitacaoUsuarioResponse = solicitacaoUsuarioMapper.toSolicitacaoUsuarioResponse(solicitacaoUsuarioCriado);
        return new ResponseEntity<>(solicitacaoUsuarioResponse, HttpStatus.CREATED);
    }


}
