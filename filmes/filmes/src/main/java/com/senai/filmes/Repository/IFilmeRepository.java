package com.senai.filmes.Repository;

import com.senai.filmes.DTO.Response.FilmeResponse;
import com.senai.filmes.Model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IFilmeRepository extends JpaRepository<Filme, UUID> {


}
