package com.Senai.Filmes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class FilmesApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void gerarHashSenha() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("Admin:   " + encoder.encode("Admin@123"));
		System.out.println("Usuario: " + encoder.encode("Usuario@123"));
	}

}
