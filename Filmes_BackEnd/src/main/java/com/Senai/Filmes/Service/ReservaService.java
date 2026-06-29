package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.ReservaRequest;
import com.Senai.Filmes.DTO.Response.AssentoResponse;
import com.Senai.Filmes.DTO.Response.ReservaResponse;
import com.Senai.Filmes.Model.Assento;
import com.Senai.Filmes.Model.Enums.StatusReserva;
import com.Senai.Filmes.Model.Reserva;
import com.Senai.Filmes.Model.ReservaAssento;
import com.Senai.Filmes.Model.Sessao;
import com.Senai.Filmes.Model.Usuario;
import com.Senai.Filmes.Repository.IAssentoRepository;
import com.Senai.Filmes.Repository.IReservaAssentoRepository;
import com.Senai.Filmes.Repository.IReservaRepository;
import com.Senai.Filmes.Repository.ISessaoRepository;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private ISessaoRepository sessaoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IAssentoRepository assentoRepository;

    @Autowired
    private IReservaAssentoRepository reservaAssentoRepository;

    @Autowired
    private SessaoService sessaoService;

    @Transactional
    public ReservaResponse criar(ReservaRequest request, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Sessao sessao = sessaoRepository.findById(request.sessaoId())
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        if (!sessao.getInicio().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Só é possível reservar sessões futuras");
        }

        // Verifica disponibilidade de TODOS os assentos antes de criar qualquer registro
        for (UUID assentoId : request.assentoIds()) {
            if (reservaAssentoRepository.isAssentoOcupado(assentoId, request.sessaoId(), StatusReserva.ATIVA)) {
                throw new IllegalStateException("Assento já reservado para esta sessão");
            }
        }

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setSessao(sessao);
        reserva.setStatus(StatusReserva.ATIVA);

        Reserva reservaSalva = reservaRepository.save(reserva);

        List<AssentoResponse> assentosResponse = new ArrayList<>();

        for (UUID assentoId : request.assentoIds()) {
            Assento assento = assentoRepository.findById(assentoId)
                    .orElseThrow(() -> new EntityNotFoundException("Assento não encontrado"));

            ReservaAssento ra = new ReservaAssento();
            ra.setReserva(reservaSalva);
            ra.setAssento(assento);
            reservaSalva.getAssentos().add(ra);

            assentosResponse.add(new AssentoResponse(assento.getId(), assento.getFileira(), assento.getNumero(), false));
        }

        return new ReservaResponse(
                reservaSalva.getId(),
                sessaoService.toResponse(sessao),
                assentosResponse,
                reservaSalva.getStatus(),
                reservaSalva.getCriadoEm()
        );
    }

    public List<ReservaResponse> listarMinhas(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return reservaRepository.findByUsuarioId(usuario.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void cancelar(UUID id, String emailUsuario) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        if (!reserva.getUsuario().getEmail().equals(emailUsuario)) {
            throw new IllegalStateException("Você não tem permissão para cancelar esta reserva");
        }

        if (!reserva.getSessao().getInicio().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Não é possível cancelar uma reserva de sessão que já começou");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    public ReservaResponse toResponse(Reserva reserva) {
        List<AssentoResponse> assentos = reserva.getAssentos().stream()
                .map(ra -> new AssentoResponse(
                        ra.getAssento().getId(),
                        ra.getAssento().getFileira(),
                        ra.getAssento().getNumero(),
                        false
                ))
                .toList();

        return new ReservaResponse(
                reserva.getId(),
                sessaoService.toResponse(reserva.getSessao()),
                assentos,
                reserva.getStatus(),
                reserva.getCriadoEm()
        );
    }
}
