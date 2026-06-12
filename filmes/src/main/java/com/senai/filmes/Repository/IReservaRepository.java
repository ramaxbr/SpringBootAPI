package com.senai.filmes.Repository;

import com.senai.filmes.Model.Reservas;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReservaRepository extends jpaRepository<Reservas, UUID>{
    List<Reservas> findByUsuario(UUID usuarioId);

}
