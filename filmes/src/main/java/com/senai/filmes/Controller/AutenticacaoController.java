package com.senai.filmes.Controller;

import com.senai.filmes.DTO.Request.CadastroRequest;
import com.senai.filmes.DTO.Request.LoginRequest;
import com.senai.filmes.DTO.Response.AuthResponse;
import com.senai.filmes.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoint para cadastro e login de usuários")
public class AutenticacaoController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/Cadastro")
    @Operation(summary = "Cadastrar usuario", description = "Cadastra um usuário e retorna o JWT Token")
    public ResponseEntity<AuthResponse> cadastrar(@RequestBody CadastroRequest request){
        return new ResponseEntity<>(authenticationService.cadastrarUsuario(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica o usuário e retorna um token JWT")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }
}
