package com.senai.filmes.Service;

import com.senai.filmes.DTO.Request.SalaRequest;
import com.senai.filmes.DTO.Response.SalaResponse;
import com.senai.filmes.Model.Assentos;
import com.senai.filmes.Model.Sala;
import com.senai.filmes.Repository.ISalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SalaService {


    @Autowired
    private ISalaRepository salaRepository;

    public SalaResponse cadastrarSala(SalaRequest request) {
        Sala sala = new Sala();
        sala.setNome(request.nome());
        sala.setTotalAssentos(request.fileiras() * request.assentosPorFileira());

        List<Assentos> assentos = gerarAssentos(sala, request.fileiras(), request.assentosPorFileira());
        sala.setAssentos(assentos);

        return toResponse(salaRepository.save(sala));
    }

    public List<SalaResponse> listarTodos(){

        return salaRepository.findAll().stream().map(this::toResponse).toList();
    }

    //get byId Buscar por id
    public SalaResponse listarSalaPorId(UUID id){
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada!"));

        return toResponse(sala);
    }

    private List<Assentos> gerarAssentos(Sala sala, int fileiras, int assentosPorFileira) {
        List<Assentos> assentos = new ArrayList<>();
        for (int f = 0; f < fileiras; f++) {
            String fileira = String.valueOf((char) ('A' + f));
            for (int n = 1; n <= assentosPorFileira; n++) {
                Assentos assento = new Assentos();
                assento.setSalas(sala);
                assento.setFileira(fileira);
                assento.setNumero(n);
                assentos.add(assento);
            }
        }
        return assentos;
    }


    //Deletar
    public void deletar(UUID id){
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma sala encontrada!"));

        salaRepository.delete(sala);
    }

    private SalaResponse toResponse(Sala sala) {
        return new SalaResponse(
                sala.getId(),
                sala.getNome(),
                sala.getTotalAssentos()
        );
    }
}
