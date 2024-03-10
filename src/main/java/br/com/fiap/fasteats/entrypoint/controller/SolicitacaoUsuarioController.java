package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.core.usecase.SolicitacaoUsuarioInputPort;
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

    private static final String  summaryAndDescritionDesativar= "Cliente solicita desativar sua conta.";
    private static final String  summaryAndDescritionExcluir= "Cliente solicita excluir sua conta.";

    @PostMapping
    @Operation(summary = summaryAndDescritionDesativar, description = summaryAndDescritionDesativar)
    public ResponseEntity<SolicitacaoUsuarioResponse> criarSolicitacaoDesativarUsuario(@Valid @RequestBody SolicitacaoUsuarioResponse solicitacaoRequest) {
        SolicitacaoUsuario solicitacaoUsuario = solicitacaoUsuarioMapper.toSolicitacaoUsuario(solicitacaoRequest);
        SolicitacaoUsuario solicitacaoUsuarioCriado = solicitacaoUsuarioInputPort.criarSolicitacaoDesativarUsuario(solicitacaoUsuario);
        SolicitacaoUsuarioResponse solicitacaoUsuarioResponse = solicitacaoUsuarioMapper.toSolicitacaoUsuarioResponse(solicitacaoUsuarioCriado);
        return new ResponseEntity<>(solicitacaoUsuarioResponse, HttpStatus.CREATED);
    }


    @PostMapping
    @Operation(summary = summaryAndDescritionExcluir, description = summaryAndDescritionExcluir)
    public ResponseEntity<SolicitacaoUsuarioResponse> criarSolicitacaoExcluirUsuario(@Valid @RequestBody SolicitacaoUsuarioResponse solicitacaoRequest) {
        SolicitacaoUsuario solicitacaoUsuario = solicitacaoUsuarioMapper.toSolicitacaoUsuario(solicitacaoRequest);
        SolicitacaoUsuario solicitacaoUsuarioCriado = solicitacaoUsuarioInputPort.criarSolicitacaoExcluirUsuario(solicitacaoUsuario);
        SolicitacaoUsuarioResponse solicitacaoUsuarioResponse = solicitacaoUsuarioMapper.toSolicitacaoUsuarioResponse(solicitacaoUsuarioCriado);
        return new ResponseEntity<>(solicitacaoUsuarioResponse, HttpStatus.CREATED);
    }

}
