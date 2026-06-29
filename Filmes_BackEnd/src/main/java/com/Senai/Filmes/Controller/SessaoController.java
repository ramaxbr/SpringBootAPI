package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.SessaoRequest;
import com.Senai.Filmes.DTO.Response.AssentoResponse;
import com.Senai.Filmes.DTO.Response.SessaoResponse;
import com.Senai.Filmes.Service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "Sessões", description = "Endpoints para gerenciamento de sessões de cinema")
@RequestMapping("/api/sessoes")
@RestController
@CrossOrigin("*")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @GetMapping
    @Operation(summary = "Listar sessões", description = "Retorna sessões por filme (filmeId) ou por data específica (data)")
    public ResponseEntity<List<SessaoResponse>> listarSessoes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) UUID filmeId) {

        List<SessaoResponse> sessoes;
        if (filmeId != null) {
            sessoes = sessaoService.listarPorFilme(filmeId);
        } else if (data != null) {
            sessoes = sessaoService.listarPorData(data);
        } else {
            throw new IllegalArgumentException("Informe o parâmetro 'data' ou 'filmeId' para filtrar as sessões");
        }

        if (sessoes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sessoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão por ID", description = "Retorna os detalhes de uma sessão")
    public ResponseEntity<SessaoResponse> buscarPorId(@PathVariable UUID id) {
        return new ResponseEntity<>(sessaoService.buscarPorId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/assentos")
    @Operation(summary = "Listar assentos disponíveis", description = "Retorna os assentos disponíveis para uma sessão")
    public ResponseEntity<List<AssentoResponse>> listarAssentos(@PathVariable UUID id) {
        return new ResponseEntity<>(sessaoService.listarAssentosDisponiveis(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar sessão", description = "Cadastra uma nova sessão de cinema — somente ADMIN")
    public ResponseEntity<SessaoResponse> criar(@RequestBody SessaoRequest request) {
        return new ResponseEntity<>(sessaoService.criar(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar sessão", description = "Remove uma sessão do sistema — somente ADMIN")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        sessaoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
