package com.senai.filmes.Service;

import com.senai.filmes.DTO.Request.SalaRequest;
import com.senai.filmes.DTO.Response.SalaResponse;
import com.senai.filmes.Model.Sala;
import com.senai.filmes.Repository.ISalaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class SalaService {

    @Autowired
    private ISalaRepository salaRepository;

    public SalaResponse cadastrarSala(SalaRequest request) {
        Sala sala = new Sala();
        sala.setNome(request.nome());
        sala.setTotalAssentos(request.fileiras() * request.assentosPorFileira());

        List<Assento> assentos = gerarAssentos(sala, request.fileiras(), request.assentosPorFileira());
        sala.setAssentos(assentos);

        return toResponse(salaRepository.save(sala));
    }

    private List<Assento> gerarAssentos(Sala sala, int fileiras, int assentosPorFileira) {
        List<Assento> assentos = new ArrayList<>();
        for (int f = 0; f < fileiras; f++) {
            String fileira = String.valueOf((char) ('A' + f));
            for (int n = 1; n <= assentosPorFileira; n++) {
                Assento assento = new Assento();
                assento.setSala(sala);
                assento.setFileira(fileira);
                assento.setNumero(n);
                assentos.add(assento);
            }
        }
        return assentos;
    }

    private SalaResponse toResponse(Sala sala) {
        return new SalaResponse(
                sala.getId(),
                sala.getNome(),
                sala.getTotalAssentos()
        );
    }
}
