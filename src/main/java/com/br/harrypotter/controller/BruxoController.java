package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.service.BruxoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bruxos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BruxoController {

    private final BruxoService service;

    @PostMapping
    public ResponseEntity<BruxoResponseDTO> criar(@RequestBody BruxoRequestDTO dto) {
        return ResponseEntity.ok(service.criarBruxo(dto));
    }

    @GetMapping
    public ResponseEntity<List<BruxoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarBruxos());
    }

    @GetMapping("/listar-nomes")
    public ResponseEntity<List<String>> listarNomes() {
        return ResponseEntity.ok(service.listarNomes());
    }

    @GetMapping("/batalha")
    public ResponseEntity<?> batalha(@RequestParam String bruxo1, @RequestParam String bruxo2) {
        try {
            BatalhaResponseDTO resultado = service.batalhar(bruxo1, bruxo2);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        service.deletarTodos();
        return ResponseEntity.noContent().build();
    }
}
