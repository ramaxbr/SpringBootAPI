package com.senai.filmes.Repository;

import com.senai.filmes.Model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISalaRepository extends JpaRepository<Sala, UUID> {

}
