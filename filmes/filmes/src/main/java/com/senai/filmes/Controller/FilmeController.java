package com.senai.filmes.Controller;

import com.senai.filmes.DTO.Response.FilmeResponse;
import com.senai.filmes.Service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        public ResponseEntity<List<FilmeResponse>> listarTodos(){
            List<FilmeResponse> filmes = filmeService.listarTodos();

            if (filmes.isEmpty()){
                return new ResponseEntity<>(filmes, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(filmes, HttpStatus.OK);
        }

//        @GetMapping("/{id}")
//        public ResponseEntity<FilmeResponse> buscarPorId(@PathVariable UUID id){
//
//        }

    }

