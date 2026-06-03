package com.senai.filmes.Service;

import com.senai.filmes.DTO.Request.FilmeRequest;
import com.senai.filmes.DTO.Response.FilmeResponse;
import com.senai.filmes.Model.Filme;
import com.senai.filmes.Repository.IFilmeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Service
public class FilmeService {

    @Autowired
    private IFilmeRepository filmeRepository;

    //CRUD
    //METODO listar todos
    public List<FilmeResponse> listarTodos(){
        return filmeRepository.findAll().stream().map(this::toResponse).toList();
    }
    public FilmeResponse buscarPorFilmeId(UUID id){
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));
        return toResponse(filme);
    }

    public FilmeResponse cadastrarFilme(FilmeRequest request){
        Filme filme = new Filme();
        filme.setTitulo(request.titulo());
        filme.setDescricao(request.descricao());
        filme.setUrlPostar(request.urlPoster());
        filme.setGenero(request.genero());
        filme.setDuracaoMinutos(request.ducaracaoMinutos());

        return toResponse(filmeRepository.save(filme));
    }

    public FilmeResponse atualizarFilme(UUID id, FilmeRequest filmeRequest) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum filme encontrado!"));
        filme.setTitulo(filmeRequest.titulo());
        filme.setDescricao(filmeRequest.descricao());
        filme.setUrlPostar(filmeRequest.urlPoster());
        filme.setGenero(filmeRequest.genero());
        filme.setDuracaoMinutos(filmeRequest.ducaracaoMinutos());
        return toResponse(filmeRepository.save(filme));
    }

    public void deletarFilme(UUID id){
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum filme encontrado!"));

        filmeRepository.delete(filme);
    }



















    private FilmeResponse toResponse(Filme filme){
        return new FilmeResponse(
                filme.getId(),
                filme.getTitulo(),
                filme.getDescricao(),
                filme.getUrlPostar(),
                filme.getGenero(),
                filme.getDuracaoMinutos()
        );
    }

}
