package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.SessaoRequest;
import com.Senai.Filmes.DTO.Response.AssentoResponse;
import com.Senai.Filmes.DTO.Response.FilmeResponse;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.DTO.Response.SessaoResponse;
import com.Senai.Filmes.Model.Enums.StatusReserva;
import com.Senai.Filmes.Model.Filme;
import com.Senai.Filmes.Model.Sala;
import com.Senai.Filmes.Model.Sessao;
import com.Senai.Filmes.Repository.IAssentoRepository;
import com.Senai.Filmes.Repository.IFilmeRepository;
import com.Senai.Filmes.Repository.IReservaAssentoRepository;
import com.Senai.Filmes.Repository.ISalaRepository;
import com.Senai.Filmes.Repository.ISessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessaoService {

    @Autowired
    private ISessaoRepository sessaoRepository;

    @Autowired
    private IFilmeRepository filmeRepository;

    @Autowired
    private ISalaRepository salaRepository;

    @Autowired
    private IAssentoRepository assentoRepository;

    @Autowired
    private IReservaAssentoRepository reservaAssentoRepository;

    public List<SessaoResponse> listarPorData(LocalDate data) {
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.plusDays(1).atStartOfDay();
        return sessaoRepository.findByData(inicioDia, fimDia).stream().map(this::toResponse).toList();
    }

    public List<SessaoResponse> listarPorFilme(UUID filmeId) {
        return sessaoRepository.findByFilmeId(filmeId).stream().map(this::toResponse).toList();
    }

    public SessaoResponse buscarPorId(UUID id) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));
        return toResponse(sessao);
    }

    public List<AssentoResponse> listarAssentosDisponiveis(UUID sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        List<UUID> ocupados = reservaAssentoRepository.findAssentosOcupadosBySessaoId(sessaoId, StatusReserva.ATIVA);

        return assentoRepository.findBySalaId(sessao.getSala().getId()).stream()
                .map(assento -> new AssentoResponse(
                        assento.getId(),
                        assento.getFileira(),
                        assento.getNumero(),
                        !ocupados.contains(assento.getId())
                ))
                .toList();
    }

    public SessaoResponse criar(SessaoRequest request) {
        Filme filme = filmeRepository.findById(request.filmeId())
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        Sala sala = salaRepository.findById(request.salaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        if (sessaoRepository.existeConflitoDeSala(request.salaId(), request.inicio(), request.fim())) {
            throw new IllegalStateException("Já existe uma sessão nessa sala nesse horário");
        }

        Sessao sessao = new Sessao();
        sessao.setFilme(filme);
        sessao.setSala(sala);
        sessao.setInicio(request.inicio());
        sessao.setFim(request.fim());
        sessao.setPreco(request.preco());

        return toResponse(sessaoRepository.save(sessao));
    }

    public void deletar(UUID id) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));
        sessaoRepository.delete(sessao);
    }

    public SessaoResponse toResponse(Sessao sessao) {
        Filme f = sessao.getFilme();
        Sala s = sessao.getSala();

        FilmeResponse filmeResponse = new FilmeResponse(
                f.getId(), f.getTitulo(), f.getDescricao(), f.getUrlPoster(), f.getGenero(), f.getDuracaoMinutos()
        );

        SalaResponse salaResponse = new SalaResponse(s.getId(), s.getNome(), s.getTotalAssentos());

        return new SessaoResponse(
                sessao.getId(), filmeResponse, salaResponse, sessao.getInicio(), sessao.getFim(), sessao.getPreco()
        );
    }
}
