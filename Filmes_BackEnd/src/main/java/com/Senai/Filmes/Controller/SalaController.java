package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.SalaRequest;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.Service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Salas", description = "Endpoints para gerenciamento de salas de cinema")
@RequestMapping("/api/salas")
@RestController
@CrossOrigin("*")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    @Operation(summary = "Listar salas", description = "Lista todas as salas cadastradas")
    public ResponseEntity<List<SalaResponse>> listarTodas() {
        List<SalaResponse> salas = salaService.listarTodas();

        if (salas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID", description = "Retorna os detalhes de uma sala")
    public ResponseEntity<SalaResponse> buscarPorId(@PathVariable UUID id) {
        return new ResponseEntity<>(salaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar sala", description = "Cadastra uma nova sala e gera os assentos automaticamente — somente ADMIN")
    public ResponseEntity<SalaResponse> criar(@RequestBody SalaRequest request) {
        return new ResponseEntity<>(salaService.criar(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar sala", description = "Remove uma sala do sistema — somente ADMIN")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        salaService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
