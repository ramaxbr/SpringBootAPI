package com.senai.filmes.Controller;


import com.senai.filmes.DTO.Request.SalaRequest;
import com.senai.filmes.DTO.Response.SalaResponse;
import com.senai.filmes.Model.Sala;
import com.senai.filmes.Service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Salas", description= "Endpoint para gerenciamento de salas do cinema")
@RestController
@CrossOrigin("*")
@RequestMapping("/api/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    //Buscar Sala
    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID", description = "Retorna os detalehs de uma única sala")
    public ResponseEntity<SalaResponse> buscarPorId(@PathVariable UUID id){
        return new ResponseEntity<>(salaService.listarSalaPorId(id), HttpStatus.OK);
    }

    //Criar Sala
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar sala", description = "Retorna a criação de uma sala no sistema COM GERAÇÃO DE ASSENTOS AUTOMÁTICOS")
    public ResponseEntity<SalaResponse> criarSala(@RequestBody SalaRequest salaRequest){
        return new ResponseEntity<>(salaService.cadastrarSala(salaRequest), HttpStatus.CREATED);
    }

    //Listar Sala
    @GetMapping
    @Operation(summary = "Listar salas", description = "Ele lista todas as salas")
    public ResponseEntity<List<SalaResponse>> listarTodos(){
        List<SalaResponse> salas = salaService.listarTodos();
        if(salas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    //Deletar Sala

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar sala", description = "deleta os dados de uma sala por completo -> Somente ADMIN")
    public ResponseEntity<SalaResponse> deletar(@PathVariable UUID id){
        salaService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
