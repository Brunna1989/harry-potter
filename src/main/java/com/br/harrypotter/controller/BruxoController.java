package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.service.BruxoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bruxos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Bruxos", description = "Gerenciamento de bruxos e batalhas mágicas")
public class BruxoController {

    private final BruxoService service;

    @PostMapping
    @Operation(summary = "Criar um novo bruxo", description = "Cria um novo bruxo com base nos dados informados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bruxo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<BruxoResponseDTO> criar(
            @Parameter(description = "Dados do bruxo a ser criado", required = true)
            @RequestBody BruxoRequestDTO dto) {
        return ResponseEntity.ok(service.criarBruxo(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os bruxos", description = "Retorna uma lista de todos os bruxos cadastrados")
    public ResponseEntity<List<BruxoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarBruxos());
    }

    @GetMapping("/listar-nomes")
    @Operation(summary = "Listar nomes dos bruxos", description = "Retorna apenas os nomes dos bruxos cadastrados")
    public ResponseEntity<List<String>> listarNomes() {
        return ResponseEntity.ok(service.listarNomes());
    }

    @GetMapping("/batalha")
    @Operation(
            summary = "Realizar uma batalha entre dois bruxos",
            description = "Recebe os nomes de dois bruxos e retorna o resultado da batalha"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Batalha realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Um ou ambos os bruxos não encontrados")
    })
    public ResponseEntity<?> batalha(
            @Parameter(description = "Nome do primeiro bruxo", required = true) @RequestParam String bruxo1,
            @Parameter(description = "Nome do segundo bruxo", required = true) @RequestParam String bruxo2) {
        try {
            BatalhaResponseDTO resultado = service.batalhar(bruxo1, bruxo2);
            return ResponseEntity.ok(resultado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("erro", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping
    @Operation(summary = "Deletar todos os bruxos", description = "Remove todos os bruxos cadastrados do sistema")
    @ApiResponse(responseCode = "204", description = "Todos os bruxos foram deletados com sucesso")
    public ResponseEntity<Void> deletarTodos() {
        service.deletarTodos();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar bruxo por ID", description = "Remove um bruxo específico com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bruxo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Bruxo não encontrado")
    })
    public ResponseEntity<?> deletarPorId(
            @Parameter(description = "ID do bruxo a ser deletado", required = true) @PathVariable Long id) {
        try {
            service.deletarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("erro", e.getMessage()));
        }
    }
}
