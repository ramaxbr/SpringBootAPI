package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.CadastroRequest;
import com.Senai.Filmes.DTO.Request.LoginRequest;
import com.Senai.Filmes.DTO.Response.AuthResponse;
import com.Senai.Filmes.Service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Endpoints para cadastro e login de usuários")
@RequestMapping("/api/auth")
@RestController
@CrossOrigin("*")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário e retorna o token JWT")
    public ResponseEntity<AuthResponse> cadastrar(@RequestBody CadastroRequest request) {
        return new ResponseEntity<>(autenticacaoService.cadastrar(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica o usuário e retorna o token JWT")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(autenticacaoService.login(request), HttpStatus.OK);
    }
}
