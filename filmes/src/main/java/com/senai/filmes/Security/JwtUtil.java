package com.senai.filmes.Security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // secret é o nome original do JWT!
    private String secret; // É o token jwt e esse token normalmente tem um tempo de expiração

    @Value("${jwt.expiration}") // O mesmo vale pra esse.
    private long expiration;


    private SecretKey getChave(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Gera um token JWT com o email do usuário como subject

    public String gerarToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername()) // subject = email
                .issuedAt(new Date()) // data de criação
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getChave()) // assina com HMAC-SHA256
                .compact();
    }

    //Extrai o email do token (sem lançar exceção)
    public String extrairEmail(String token){
        return Jwts.parser().verifyWith(getChave()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    //Valida se o token pertence ao usuário e ainda não expirou
    public boolean validarToken(String token, UserDetails userDetails){
        String email = extrairEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpirado(token);
    }

    private boolean isTokenExpirado(String token){
        return Jwts.parser().verifyWith(getChave()).build()
                .parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }
}
