package com.senai.filmes.Repository;

import com.senai.filmes.Model.Enums.StatusReserva;
import com.senai.filmes.Model.ReservaAssento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReservaAssentoRepository extends JpaRepository<ReservaAssento, UUID> {

    @Query("SELECT CASE WHEN COUNT(ra) > 0 THEN true ELSE false END" +
            " FROM ReservaAssento ra" +
            " WHERE ra.assento.id = :assentoId AND ra.reservas.sessao.sessao_id = :sessaoId AND ra.reservas.status = :status"
    )
    boolean isAssentoOcupado(
            @Param("assentoId") UUID assentoId,
            @Param("sessaoId") UUID sessaoId,
            @Param("status")StatusReserva statusReserva
            );
    @Query("SELECT ra.assento.id FROM ReservaAssento ra " +
    " WHERE ra.reservas.id = sessaoId AND ra.reservas.status = :status")
    List<UUID> findAssentosOcupadosBySessaoId(@Param("sessaoId") UUID sessaoId,
                                              @Param("status") StatusReserva statusReserva);




}
