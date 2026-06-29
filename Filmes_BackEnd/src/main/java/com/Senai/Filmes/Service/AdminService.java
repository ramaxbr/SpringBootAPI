package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Response.RelatorioResponse;
import com.Senai.Filmes.DTO.Response.ReservaResponse;
import com.Senai.Filmes.Model.Enums.Cargo;
import com.Senai.Filmes.Model.Enums.StatusReserva;
import com.Senai.Filmes.Model.Reserva;
import com.Senai.Filmes.Model.Usuario;
import com.Senai.Filmes.Repository.IReservaRepository;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ReservaService reservaService;

    public List<ReservaResponse> listarTodasReservas() {
        return reservaRepository.findAll().stream().map(reservaService::toResponse).toList();
    }

    public RelatorioResponse gerarRelatorio() {
        List<Reserva> todas = reservaRepository.findAll();

        long totalReservas = todas.stream()
                .filter(r -> r.getStatus() == StatusReserva.ATIVA)
                .count();

        BigDecimal totalReceita = todas.stream()
                .filter(r -> r.getStatus() == StatusReserva.ATIVA)
                .map(r -> r.getSessao().getPreco().multiply(BigDecimal.valueOf(r.getAssentos().size())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Long> porFilme = todas.stream()
                .filter(r -> r.getStatus() == StatusReserva.ATIVA)
                .collect(Collectors.groupingBy(
                        r -> r.getSessao().getFilme().getTitulo(),
                        Collectors.counting()
                ));

        List<RelatorioResponse.FilmeTotais> filmes = porFilme.entrySet().stream()
                .map(e -> new RelatorioResponse.FilmeTotais(e.getKey(), e.getValue()))
                .toList();

        return new RelatorioResponse(totalReservas, totalReceita, filmes);
    }

    public void promoverParaAdmin(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setCargo(Cargo.ADMIN);
        usuarioRepository.save(usuario);
    }
}
