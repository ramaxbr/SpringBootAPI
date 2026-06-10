package com.senai.filmes.Service;

import com.senai.filmes.DTO.Request.LoginRequest;
import com.senai.filmes.DTO.Response.AuthResponse;
import com.senai.filmes.Model.Usuario;
import com.senai.filmes.Repository.IUsuarioRepository;
import com.senai.filmes.Security.JwtUtil;
import com.senai.filmes.Security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    //Cadastrar(novo usuário)



    //Login

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha())
        );

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.email());
        String token = jwUtil.gerarToken(userDetails);

        return new AuthResponse(token, usuario.getNome(), usuario.getCargo().name());
    }

}
