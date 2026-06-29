package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.ReservaRequest;
import com.Senai.Filmes.DTO.Response.ReservaResponse;
import com.Senai.Filmes.Service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Reservas", description = "Endpoints para gerenciamento de reservas")
@RequestMapping("/api/reservas")
@RestController
@CrossOrigin("*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @Operation(summary = "Fazer reserva", description = "Realiza uma reserva de assentos para uma sessão")
    public ResponseEntity<ReservaResponse> criar(
            @RequestBody ReservaRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(reservaService.criar(request, userDetails.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @Operation(summary = "Minhas reservas", description = "Lista todas as reservas do usuário autenticado")
    public ResponseEntity<List<ReservaResponse>> listarMinhas(@AuthenticationPrincipal UserDetails userDetails) {
        List<ReservaResponse> reservas = reservaService.listarMinhas(userDetails.getUsername());
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva do usuário autenticado")
    public ResponseEntity<Void> cancelar(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        reservaService.cancelar(id, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
