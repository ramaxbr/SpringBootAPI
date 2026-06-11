package com.senai.filmes.Controller;

import com.senai.filmes.DTO.Request.FilmeRequest;
import com.senai.filmes.DTO.Response.FilmeResponse;
import com.senai.filmes.Service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController //Classe líder para classifi o API.
@CrossOrigin("*") //Liberar acesso interno na rede para a API filmes ou externo colocando o IP.
@RequestMapping("/api/filmes")
public class FilmeController {

        @Autowired
        private FilmeService filmeService;

        @GetMapping
        @Operation(summary = "Listar todos os filmes", description = "Rota para listar todos os filmes cadastrados.")
        public ResponseEntity<List<FilmeResponse>> listarTodos(){
            List<FilmeResponse> filmes = filmeService.listarTodos();

            if (filmes.isEmpty()){
                return new ResponseEntity<>(filmes, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(filmes, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Buscar filmes por ID", description = "Retorna os detalhes de um único filme.")
        public ResponseEntity<FilmeResponse> buscarPorId(@PathVariable UUID id){
            return new ResponseEntity<>(filmeService.buscarPorFilmeId(id), HttpStatus.OK);
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Criar filmes", description = "Cadastrar um novo filme.")
    public ResponseEntity<FilmeResponse> criarFilme(@RequestBody FilmeRequest filmerequest){
            return new ResponseEntity<>(filmeService.cadastrarFilme(filmerequest), HttpStatus.CREATED);
        }


        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Atualizar filme", description = "Atualizar os dados de um filme")
        public ResponseEntity<FilmeResponse> atualizar(@PathVariable UUID id, @RequestBody FilmeRequest filmeRequest){
            return new ResponseEntity<>(filmeService.atualizarFilme(id, filmeRequest), HttpStatus.OK);
        }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar filme", description = "Deletar filme")
    public ResponseEntity<FilmeResponse> deletar(@PathVariable UUID id) {
        filmeService.deletarFilme(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}




