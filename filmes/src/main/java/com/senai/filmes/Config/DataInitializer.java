package com.senai.filmes.Config;

import com.senai.filmes.Model.Enums.Cargo;
import com.senai.filmes.Model.Usuario;
import com.senai.filmes.Repository.IUsuarioRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataInitializer implements CommandLineRunner {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    // Lê do application.properties (com valor padrão para desenvolvimento)
    //@Value("${admin.email:admin@cinema.com}")
    private String adminEmail;

    //@Value(staticConstructor = "${admin.senha:Admin@123}")
    private String adminSenha;

    @Override
    public void run(String... args) {
        // Se o admin já existe, não faz nada (evita duplicação a cada restart)
        if (usuarioRepository.existsByEmail(adminEmail)) {
            return;
        }

        // Cria o admin inicial com senha hasheada
        Usuario admin = new Usuario();
        admin.setNome("Administrador");
        admin.setEmail(adminEmail);
        admin.setSenha(passwordEncoder.encode(adminSenha)); // NUNCA salvar senha pura!
        admin.setCargo(Cargo.ADMIN);

        usuarioRepository.save(admin);
        System.out.println(">>> Admin criado: " + adminEmail);
    }
}
