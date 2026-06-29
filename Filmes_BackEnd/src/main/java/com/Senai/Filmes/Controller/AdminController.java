package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Response.RelatorioResponse;
import com.Senai.Filmes.DTO.Response.ReservaResponse;
import com.Senai.Filmes.Service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Administração", description = "Endpoints administrativos — somente ADMIN")
@RequestMapping("/api/admin")
@RestController
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/reservas")
    @Operation(summary = "Listar todas as reservas", description = "Retorna todas as reservas do sistema")
    public ResponseEntity<List<ReservaResponse>> listarTodasReservas() {
        List<ReservaResponse> reservas = adminService.listarTodasReservas();
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @GetMapping("/relatorios")
    @Operation(summary = "Gerar relatório", description = "Gera um relatório geral do sistema")
    public ResponseEntity<RelatorioResponse> gerarRelatorio() {
        return new ResponseEntity<>(adminService.gerarRelatorio(), HttpStatus.OK);
    }

    @PatchMapping("/usuarios/{id}/promover")
    @Operation(summary = "Promover usuário", description = "Promove um usuário para ADMIN")
    public ResponseEntity<Void> promoverParaAdmin(@PathVariable UUID id) {
        adminService.promoverParaAdmin(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
