package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.FilmeRequest;
import com.Senai.Filmes.DTO.Response.FilmeResponse;
import com.Senai.Filmes.Model.Filme;
import com.Senai.Filmes.Repository.IFilmeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class FilmeService {

    @Autowired
    private IFilmeRepository filmeRepository;

    @Autowired
    private S3Service s3Service;

    public List<FilmeResponse> listarTodos() {
        return filmeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public FilmeResponse buscarPorId(UUID id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));
        return toResponse(filme);
    }

    public FilmeResponse criar(FilmeRequest request) {
        Filme filme = new Filme();
        filme.setTitulo(request.titulo());
        filme.setDescricao(request.descricao());
        filme.setGenero(request.genero());
        filme.setDuracaoMinutos(request.duracaoMinutos());
        return toResponse(filmeRepository.save(filme));
    }

    public FilmeResponse atualizar(UUID id, FilmeRequest request) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        filme.setTitulo(request.titulo());
        filme.setDescricao(request.descricao());
        filme.setGenero(request.genero());
        filme.setDuracaoMinutos(request.duracaoMinutos());

        return toResponse(filmeRepository.save(filme));
    }

    public FilmeResponse uploadImagem(UUID id, MultipartFile imagem) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        s3Service.deletar(filme.getUrlPoster());
        filme.setUrlPoster(s3Service.upload(imagem));

        return toResponse(filmeRepository.save(filme));
    }

    public FilmeResponse removerImagem(UUID id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        s3Service.deletar(filme.getUrlPoster());
        filme.setUrlPoster(null);

        return toResponse(filmeRepository.save(filme));
    }

    public void deletar(UUID id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));
        s3Service.deletar(filme.getUrlPoster());
        filmeRepository.delete(filme);
    }

    private FilmeResponse toResponse(Filme filme) {
        return new FilmeResponse(
                filme.getId(),
                filme.getTitulo(),
                filme.getDescricao(),
                filme.getUrlPoster(),
                filme.getGenero(),
                filme.getDuracaoMinutos()
        );
    }
}
