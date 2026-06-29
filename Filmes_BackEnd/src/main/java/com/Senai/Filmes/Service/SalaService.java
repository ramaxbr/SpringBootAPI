package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.SalaRequest;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.Model.Assento;
import com.Senai.Filmes.Model.Sala;
import com.Senai.Filmes.Repository.ISalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SalaService {

    @Autowired
    private ISalaRepository salaRepository;

    public List<SalaResponse> listarTodas() {
        return salaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public SalaResponse buscarPorId(UUID id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));
        return toResponse(sala);
    }

    public SalaResponse criar(SalaRequest request) {
        Sala sala = new Sala();
        sala.setNome(request.nome());
        sala.setTotalAssentos(request.totalAssentos());

        gerarAssentos(sala, request.fileiras(), request.assentosPorFileira());

        return toResponse(salaRepository.save(sala));
    }

    public void deletar(UUID id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));
        salaRepository.delete(sala);
    }

    private void gerarAssentos(Sala sala, int fileiras, int assentosPorFileira) {
        char letra = 'A';
        for (int f = 0; f < fileiras; f++) {
            for (int n = 1; n <= assentosPorFileira; n++) {
                Assento assento = new Assento();
                assento.setSala(sala);
                assento.setFileira(String.valueOf(letra));
                assento.setNumero(n);
                sala.getAssentos().add(assento);
            }
            letra++;
        }
    }

    private SalaResponse toResponse(Sala sala) {
        return new SalaResponse(sala.getId(), sala.getNome(), sala.getTotalAssentos());
    }
}
