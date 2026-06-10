package com.senai.filmes.Security;

import com.senai.filmes.Model.Usuario;
import com.senai.filmes.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        //IMPORTANTE: o Spring Security exige o prefixo "ROLE_" nas authorities
        // Por isso "ROLE_" + cargo.name() resulta em "ROLE_ADMIN" ou "ROLE_USUARIO"
        // iSSO PERMITE USAR @PreAuthorize("hasRole('ADMIN')") nos Controllers
        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getCargo().name()))
        );

    }
}
